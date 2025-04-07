package com.example.demo.web.controller;

import com.example.demo.mappers.BookMapper;
import com.example.demo.web.models.BookRequest;
import com.example.demo.web.models.BookResponse;
import com.example.demo.web.services.BookService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {
  private final BookService bookService;
  private final BookMapper bookMapper;

  @GetMapping
  public ResponseEntity<List<BookResponse>> getAllBooks() {
    return ResponseEntity.ok(bookMapper.bookDtoListToBookResponseList(bookService.findAll()));
  }

  @GetMapping("/{id}")
  public ResponseEntity<BookResponse> getBookById(@PathVariable Long id) {
    return ResponseEntity.ok(bookMapper.bookDtoToBookResponse(bookService.findById(id)));
  }

  @GetMapping("/category/{categoryName}")
  public ResponseEntity<List<BookResponse>> getAllBooksByCategoryName(
      @PathVariable String categoryName) {
    return ResponseEntity.ok(
        bookMapper.bookDtoListToBookResponseList(bookService.findAllByCategoryName(categoryName)));
  }

  @GetMapping("/title/{title}/author/{author}")
  public ResponseEntity<BookResponse> getBookByTitleAndAuthor(
      @PathVariable String title, @PathVariable String author) {
    return ResponseEntity.ok(
        bookMapper.bookDtoToBookResponse(bookService.findByTitleAndAuthor(title, author)));
  }

  @PostMapping()
  public ResponseEntity<BookResponse> createBook(@Valid @RequestBody BookRequest request) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(bookMapper.bookDtoToBookResponse(bookService.create(request)));
  }

  @PutMapping("/{id}")
  public ResponseEntity<BookResponse> updateBookById(
      @PathVariable Long id, @Valid @RequestBody BookRequest request) {
    return ResponseEntity.ok(bookMapper.bookDtoToBookResponse(bookService.update(id, request)));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteBookById(@PathVariable Long id) {
    bookService.deleteById(id);
    return ResponseEntity.noContent().build();
  }
}
