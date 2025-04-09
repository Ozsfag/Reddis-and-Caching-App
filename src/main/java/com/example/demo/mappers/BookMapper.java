package com.example.demo.mappers;

import com.example.demo.dto.BookDto;
import com.example.demo.entities.Book;
import com.example.demo.mappers.utils.CategoryMapperUtil;
import com.example.demo.web.models.BookRequest;
import com.example.demo.web.models.BookResponse;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring",
    uses = {CategoryMapper.class, CategoryMapperUtil.class},
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

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "title", source = "bookRequest.title")
  @Mapping(target = "author", source = "bookRequest.author")
  @Mapping(
      target = "category",
      qualifiedByName = {"CategoryMapperUtil", "getCategoryByCategoryId"},
      source = "bookRequest.categoryId")
  Book bookRequestToBook(BookRequest bookRequest);

  @Mapping(target = "id", source = "bookDto.id")
  @Mapping(target = "title", source = "bookDto.title")
  @Mapping(target = "author", source = "bookDto.author")
  @Mapping(
      target = "category",
      qualifiedByName = {"CategoryMapper", "categoryDtoToCategory"},
      source = "bookDto.categoryDto")
  Book bookDtoToBook(BookDto bookDto);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "title", source = "bookRequest.title")
  @Mapping(target = "author", source = "bookRequest.author")
  @Mapping(
      target = "category",
      qualifiedByName = {"CategoryMapperUtil", "getCategoryByCategoryId"},
      source = "bookRequest.categoryId")
  Book updateBookFromRequest(BookRequest bookRequest, @MappingTarget Book book);

  List<BookDto> bookListToBookDtoList(List<Book> book);

  List<BookResponse> bookDtoListToBookResponseList(List<BookDto> bookDtoList);
}
