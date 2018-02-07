package com.thymeleaf.bookstore.service.impl;

import com.thymeleaf.bookstore.model.Book;
import com.thymeleaf.bookstore.model.Order;
import com.thymeleaf.bookstore.model.OrderBook;
import com.thymeleaf.bookstore.model.User;
import com.thymeleaf.bookstore.repository.OrderRepository;
import com.thymeleaf.bookstore.repository.UserRepository;
import com.thymeleaf.bookstore.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.util.*;

@Service
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final UserRepository userRepository;

    private Order order = new Order();

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void AddBookToOrder(Book book) {
        OrderBook orderBook = order.getBooks().stream()
                .filter(b -> Objects.equals(b.getBook(), book))
                .findAny()
                .orElse(null);
        if (orderBook != null) {
            orderBook.setQuantity(orderBook.getQuantity() + 1);
        } else {
            order.addBook(book, 1L);
        }
        this.recalculate();
    }

    @Override
    public void updateQuantity(Book book, Long quantity) {
        OrderBook orderBook = order.getBooks().stream()
                .filter(b -> Objects.equals(b.getBook(), book))
                .findAny()
                .orElse(null);
        if (orderBook != null) {
            orderBook.setQuantity(quantity);
            this.recalculate();
        }
    }

    @Override
    public void removeBookFromOrder(Book book) {
        order.removeBook(book);
        this.recalculate();
    }

    private void recalculate() {
        BigDecimal orderPrice = new BigDecimal(0);
        for (OrderBook books : order.getBooks()) {
            orderPrice = orderPrice.add(
                    books.getBook()
                            .getBookPrice()
                            .multiply(new BigDecimal(books.getQuantity()))
            );
        }
        order.setOrderPrice(orderPrice);
    }

    @Override
    public void createOrder(String userName) {
        User user = userRepository.findByUserName(userName);
        order.setUser(user);
        order.setOrderDate(Calendar.getInstance().getTime());
        orderRepository.save(order);
        order = new Order();
    }

    @Override
    public List<Order> findAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public List<Order> findByOrderStatus(boolean orderStatus) {
        return orderRepository.findByOrderStatus(orderStatus);
    }

    @Override
    public Order findOrderById(Long orderId) {
        return orderRepository.findOne(orderId);
    }


    @Override
    public Order saveOrder(Order order) {
        for (OrderBook orderBook : order.getBooks()) {
            orderBook.setOrder(order);
            orderBook.getBook().getOrders().add(orderBook);
        }

        order.setOrderDate(Calendar.getInstance().getTime());

        return orderRepository.save(order);
    }

    @Override
    public void updateOrder(Order order) {
        orderRepository.save(order);
    }

    @Override
    public void deleteOrder(Order order) {
        orderRepository.delete(order);
    }

    @Override
    public List<Order> findByUserName(String userName) {
        User user = userRepository.findByUserName(userName);
        return orderRepository.findByUser(user);
    }

    @Override
    public Order getOrder() {
        return order;
    }
}
