package com.example.demo.mappers;

import com.example.demo.dto.BookDto;
import com.example.demo.entities.Book;
import com.example.demo.web.models.BookRequest;
import com.example.demo.web.models.BookResponse;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring",
    uses = CategoryMapper.class,
    unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BookMapper {

  @Mapping(target = "id", source = "book.id")
  @Mapping(target = "title", source = "book.title")
  @Mapping(target = "author", source = "book.author")
  @Mapping(
      target = "categoryDto",
      qualifiedByName = "categoryToCategoryDto",
      source = "book.category")
  BookDto bookToBookDto(Book book);

  @Mapping(target = "id", source = "bookDto.id")
  @Mapping(target = "title", source = "bookDto.title")
  @Mapping(target = "author", source = "bookDto.author")
  @Mapping(
      target = "categoryResponse",
      qualifiedByName = "categoryDtoToCategoryResponse",
      source = "categoryDto")
  BookResponse bookDtoToBookResponse(BookDto bookDto);

  @Mapping(target = "title", source = "bookRequest.title")
  @Mapping(target = "author", source = "bookRequest.author")
  @Mapping(
      target = "category",
      qualifiedByName = "categoryRequestToCategory",
      source = "bookRequest.categoryRequest")
  Book bookRequestToBook(BookRequest bookRequest);

  @Mapping(target = "id", source = "bookDto.id")
  @Mapping(target = "title", source = "bookDto.title")
  @Mapping(target = "author", source = "bookDto.author")
  @Mapping(
      target = "category",
      qualifiedByName = "categoryDtoToCategory",
      source = "bookDto.categoryDto")
  Book bookDtoToBook(BookDto bookDto);

  @Mapping(target = "title", source = "bookRequest.title")
  @Mapping(target = "author", source = "bookRequest.author")
  @Mapping(
      target = "category",
      qualifiedByName = "categoryRequestToCategory",
      source = "bookRequest.categoryRequest")
  Book updateBookFromRequest(BookRequest bookRequest, @MappingTarget Book book);

  List<BookDto> bookListToBookDtoList(List<Book> book);

  List<BookResponse> bookDtoListToBookResponseList(List<BookDto> bookDtoList);
}
