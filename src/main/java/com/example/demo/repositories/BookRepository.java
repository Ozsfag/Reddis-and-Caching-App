package com.example.demo.repositories;

import com.example.demo.entities.Book;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
  Optional<Book> findByTitleAndAuthor(String title, String author);

  List<Book> findAllByCategoryName(String categoryName);
}
