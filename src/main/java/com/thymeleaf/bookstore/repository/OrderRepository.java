package com.thymeleaf.bookstore.repository;

import com.thymeleaf.bookstore.model.Order;
import com.thymeleaf.bookstore.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long>{

    List<Order> findByUser(User user);

    List<Order> findByOrderStatus(boolean orderStatus);
}
