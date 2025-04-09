package com.example.demo.web.services;

import com.example.demo.dto.CategoryDto;
import com.example.demo.exceptions.NotFoundException;
import com.example.demo.mappers.CategoryMapper;
import com.example.demo.repositories.CategoryRepository;
import com.example.demo.web.models.CategoryRequest;
import java.text.MessageFormat;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryService {
  private final CategoryRepository categoryRepository;
  private final CategoryMapper categoryMapper;

  @Transactional(readOnly = true)
  @Cacheable(value = "categories")
  public List<CategoryDto> findAll() {
    log.info("CategoryService executing findAll method.");
    return categoryMapper.categoryListToCatgoryDtoList(categoryRepository.findAll());
  }

  @Transactional(readOnly = true)
  @Cacheable(value = "categoryIds", key = "#id")
  public CategoryDto findById(Long id) {
    log.info("CategoryService executing findById method.");
    return categoryMapper.categoryToCategoryDto(
        categoryRepository
            .findById(id)
            .orElseThrow(
                () ->
                    new NotFoundException(
                        MessageFormat.format("Category with ID is: {0} not found.", id))));
  }

  @Transactional
  @CachePut(value = "categoryIds", key = "#result.id")
  public CategoryDto create(CategoryRequest categoryRequest) {
    log.info("Executing create method.");
    var category = categoryMapper.categoryRequestToCategory(categoryRequest);
    var savedCategory = categoryRepository.save(category);
    return categoryMapper.categoryToCategoryDto(savedCategory);
  }

  @Transactional
  @Caching(
      evict = {
        @CacheEvict(value = "categoryIds", key = "#id"),
        @CacheEvict(value = "categories", allEntries = true)
      })
  public CategoryDto update(Long id, CategoryRequest categoryRequest) {
    log.info("CategoryService executing update method.");
    var existedCategory = categoryMapper.categoryDtoToCategory(findById(id));
    var updatedCategory =
        categoryMapper.updateCategoryFromCategoryRequest(categoryRequest, existedCategory);
    return categoryMapper.categoryToCategoryDto(categoryRepository.save(updatedCategory));
  }

  @Transactional
  @Caching(
      evict = {
        @CacheEvict(value = "categoryIds", key = "#id"),
        @CacheEvict(value = "categories", allEntries = true)
      })
  public void deleteById(Long id) {
    log.info("CategoryService executing delete method.");
    var existedCategoryDto = findById(id);
    categoryRepository.delete(categoryMapper.categoryDtoToCategory(existedCategoryDto));
  }
}
