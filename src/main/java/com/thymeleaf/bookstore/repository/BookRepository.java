package com.thymeleaf.bookstore.repository;

import com.thymeleaf.bookstore.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long>{
}
