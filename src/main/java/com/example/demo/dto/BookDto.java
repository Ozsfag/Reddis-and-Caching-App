package com.example.demo.dto;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDto implements Serializable {
  private Long id;
  private String title;
  private String author;
  private CategoryDto categoryDto;
}
