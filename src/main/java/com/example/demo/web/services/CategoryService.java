package com.example.demo.web.services;

import com.example.demo.dto.CategoryDto;
import com.example.demo.exceptions.NotFoundException;
import com.example.demo.mappers.CategoryMapper;
import com.example.demo.repositories.CategoryRepository;
import com.example.demo.web.models.CategoryRequest;
import jakarta.transaction.Transactional;
import java.text.MessageFormat;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryService {
  private final CategoryRepository categoryRepository;
  private final CategoryMapper categoryMapper;

  public List<CategoryDto> findAll() {
    log.info("CategoryService executing findAll method.");
    return categoryMapper.categoryListToCatgoryDtoList(categoryRepository.findAll());
  }

  @Cacheable(value = "categoryIds", key = "#id")
  public CategoryDto findById(Long id) {
    log.info("CategoryService executing findById method.");
    return categoryMapper.categoryToCategoryDto(
        categoryRepository
            .findById(id)
            .orElseThrow(
                () ->
                    new NotFoundException(
                        MessageFormat.format("Category with ID is: {} not found.", id))));
  }

  @Transactional
  @CacheEvict(
      value = {"categoryIds"},
      allEntries = true)
  public CategoryDto create(CategoryRequest categoryRequest) {
    log.info("CategoryService executing create method.");
    return categoryMapper.categoryToCategoryDto(
        categoryRepository.save(categoryMapper.categoryRequestToCategory(categoryRequest)));
  }

  @Transactional
  @CacheEvict(value = "categoryIds", allEntries = true)
  public CategoryDto update(Long id, CategoryRequest categoryRequest) {
    log.info("CategoryService executing update method.");
    var existedCategory = categoryMapper.categoryDtoToCategory(findById(id));
    var updatedCategory =
        categoryMapper.updateCategoryFromCategoryRequest(categoryRequest, existedCategory);
    return categoryMapper.categoryToCategoryDto(categoryRepository.save(updatedCategory));
  }

  @Transactional
  @CacheEvict(value = "categoryIds", allEntries = true)
  public void deleteById(Long id) {
    log.info("CategoryService executing delete method.");
    var existedCategoryDto = findById(id);
    categoryRepository.delete(categoryMapper.categoryDtoToCategory(existedCategoryDto));
  }
}
