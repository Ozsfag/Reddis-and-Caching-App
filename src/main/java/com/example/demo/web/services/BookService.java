package com.example.demo.web.services;

import com.example.demo.dto.BookDto;
import com.example.demo.exceptions.NotFoundException;
import com.example.demo.mappers.BookMapper;
import com.example.demo.repositories.BookRepository;
import com.example.demo.web.models.BookRequest;
import java.text.MessageFormat;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookService {
  private final BookRepository bookRepository;
  private final BookMapper bookMapper;

  public List<BookDto> findAll() {
    log.info("BookService executing findAll method.");
    return bookMapper.bookListToBookDtoList(bookRepository.findAll());
  }

  @Cacheable(value = "bookIds", key = "#id")
  public BookDto findById(Long id) {
    log.info("BookService executing findById method.");
    return bookMapper.bookToBookDto(
        bookRepository
            .findById(id)
            .orElseThrow(
                () ->
                    new NotFoundException(
                        MessageFormat.format("Book with ID is: {0} not found.", id))));
  }

  @Cacheable(value = "bookCategories", key = "#categoryName")
  public List<BookDto> findAllByCategoryName(String categoryName) {
    log.info("BookService executing findAllByCategoryName method.");
    return bookMapper.bookListToBookDtoList(bookRepository.findAllByCategoryName(categoryName));
  }

  @Cacheable(value = "bookTitleAndAuthor", key = "#title" + "_" + "#author")
  public BookDto findByTitleAndAuthor(String title, String author) {
    log.info("BookService executing findByTitleAndAuthor method.");
    return bookMapper.bookToBookDto(
        bookRepository
            .findByTitleAndAuthor(title, author)
            .orElseThrow(
                () ->
                    new NotFoundException(
                        MessageFormat.format(
                            "Book with title: {0} and author: {1} not found.", title, author))));
  }

  @Transactional
  @CacheEvict(
      value = {"bookIds", "bookCategories", "bookTitleAndAuthor"},
      allEntries = true)
  public BookDto create(BookRequest request) {
    log.info("bookService executing create method.");
    return bookMapper.bookToBookDto(bookRepository.save(bookMapper.bookRequestToBook(request)));
  }

  @Transactional
  @CacheEvict(
      value = {"bookIds", "bookCategories", "bookTitleAndAuthor"},
      allEntries = true)
  public BookDto update(Long id, BookRequest request) {
    log.info("BookService executing method update.");
    var existedBook = bookMapper.bookDtoToBook(findById(id));
    var updatedBook = bookMapper.updateBookFromRequest(request, existedBook);
    return bookMapper.bookToBookDto(bookRepository.save(updatedBook));
  }

  @Transactional
  @CacheEvict(
      value = {"bookIds", "bookCategories", "bookTitleAndAuthor"},
      allEntries = true)
  public void deleteById(Long id) {
    log.info("BookService executing delete method.");
    var existedBookDto = findById(id);
    bookRepository.delete(bookMapper.bookDtoToBook(existedBookDto));
  }
}
