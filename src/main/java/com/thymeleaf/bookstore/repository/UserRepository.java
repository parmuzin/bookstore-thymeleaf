package com.thymeleaf.bookstore.repository;

import com.thymeleaf.bookstore.model.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUserName(String userName);

    @EntityGraph(attributePaths = "authorities")
    User findOneWithAuthoritiesByUserName(String userName);

}
