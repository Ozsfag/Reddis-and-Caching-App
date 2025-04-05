package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@AllArgsConstructor
@Data
public class Book {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String title;
  private String author;

  @ManyToOne
  @JoinColumn(columnDefinition = "category_id", nullable = false)
  private Category category;
}
