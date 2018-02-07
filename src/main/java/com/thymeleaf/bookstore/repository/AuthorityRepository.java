package com.thymeleaf.bookstore.repository;

import com.thymeleaf.bookstore.model.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, String>{
}
