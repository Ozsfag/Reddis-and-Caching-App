package com.example.demo.mappers.utils;

import com.example.demo.entities.Category;
import com.example.demo.mappers.CategoryMapper;
import com.example.demo.web.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Named("CategoryMapperUtil")
public class CategoryMapperUtil {
  private final CategoryService categoryService;
  private final CategoryMapper categoryMapper;

  @Named("getCategoryByCategoryId")
  public Category getCategoryIdFromCategoryRequest(Long id) {
    var existedCategoryDto = categoryService.findById(id);
    return categoryMapper.categoryDtoToCategory(existedCategoryDto);
  }
}
