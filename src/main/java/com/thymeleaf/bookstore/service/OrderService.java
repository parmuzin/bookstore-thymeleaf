package com.thymeleaf.bookstore.service;

import com.thymeleaf.bookstore.model.Book;
import com.thymeleaf.bookstore.model.Order;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface OrderService {

    void AddBookToOrder(Book book);

    void updateQuantity(Book book, Long quantity);

    void removeBookFromOrder(Book book);

    Order getOrder();

    void createOrder(String userName);

    List<Order> findAllOrders();

    List<Order> findByOrderStatus(boolean orderStatus);

    Order findOrderById(Long orderId);

    Order saveOrder(Order order);

    void updateOrder(Order order);

    void deleteOrder(Order order);

    List<Order> findByUserName(String userName);
}
