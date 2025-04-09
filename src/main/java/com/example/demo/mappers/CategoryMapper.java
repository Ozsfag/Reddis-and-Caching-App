package com.example.demo.mappers;

import com.example.demo.dto.CategoryDto;
import com.example.demo.entities.Category;
import com.example.demo.web.models.CategoryRequest;
import com.example.demo.web.models.CategoryResponse;
import java.util.List;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
@Named("CategoryMapper")
public interface CategoryMapper {

  @Named("categoryToCategoryDto")
  @Mapping(target = "id", source = "id")
  @Mapping(target = "name", source = "name")
  CategoryDto categoryToCategoryDto(Category category);

  @Named("categoryDtoToCategory")
  @Mapping(target = "id", source = "id")
  @Mapping(target = "name", source = "name")
  Category categoryDtoToCategory(CategoryDto categoryDto);

  @Named("categoryDtoToCategoryResponse")
  @Mapping(target = "id", source = "id")
  @Mapping(target = "name", source = "name")
  CategoryResponse categoryDtoToCategoryResponse(CategoryDto categoryDto);

  @Named("categoryRequestToCategory")
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "name", source = "name")
  Category categoryRequestToCategory(CategoryRequest categoryRequest);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "name", source = "name")
  Category updateCategoryFromCategoryRequest(
      CategoryRequest categoryRequest, @MappingTarget Category category);

  List<CategoryDto> categoryListToCatgoryDtoList(List<Category> categoryList);

  List<CategoryResponse> categoryDtoListToCategoryResponseList(List<CategoryDto> categoryDtoList);
}
