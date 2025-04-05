package com.example.demo.web.controller;

import com.example.demo.mappers.CategoryMapper;
import com.example.demo.web.models.CategoryRequest;
import com.example.demo.web.models.CategoryResponse;
import com.example.demo.web.services.CategoryService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

  private final CategoryService categoryService;
  private final CategoryMapper categoryMapper;

  @GetMapping
  public ResponseEntity<List<CategoryResponse>> getAllCategories() {
    return ResponseEntity.ok(
        categoryMapper.categoryDtoListToCategoryResponseList(categoryService.findAll()));
  }

  @GetMapping("/{id}")
  public ResponseEntity<CategoryResponse> findCategoryById(@PathVariable Long id) {
    return ResponseEntity.ok(
        categoryMapper.categoryDtoToCategoryResponse(categoryService.findById(id)));
  }

  @PostMapping
  public ResponseEntity<CategoryResponse> createCategory(
      @Valid @RequestBody CategoryRequest categoryRequest) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(
            categoryMapper.categoryDtoToCategoryResponse(categoryService.create(categoryRequest)));
  }

  @PutMapping("/{id}")
  public ResponseEntity<CategoryResponse> updateCategory(
      @PathVariable Long id, @Valid @RequestBody CategoryRequest categoryRequest) {
    return ResponseEntity.ok(
        categoryMapper.categoryDtoToCategoryResponse(categoryService.update(id, categoryRequest)));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteCategoryById(@PathVariable Long id) {
    categoryService.deleteById(id);
    return ResponseEntity.noContent().build();
  }
}
