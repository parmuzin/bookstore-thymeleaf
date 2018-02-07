package com.thymeleaf.bookstore.controller;

import com.thymeleaf.bookstore.service.BookService;
import com.thymeleaf.bookstore.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
public class OrderController {

    private final OrderService orderService;
    private final BookService bookService;

    @Autowired
    public OrderController(OrderService orderService, BookService bookService) {
        this.orderService = orderService;
        this.bookService = bookService;
    }

    @GetMapping("/order")
    public String getOrder(Model model) {
        model.addAttribute("order", orderService.getOrder());
        return "order";
    }

    @GetMapping("/order/createOrder")
    public String createOrder(Model model, Principal principal) {
        if (orderService.getOrder().getBooks().isEmpty()) {
            model.addAttribute("errorMessage", "Order is empty");
            return getOrder(model);
        }
        orderService.createOrder(principal.getName());
        model.addAttribute("successMessage", "Order has been created");
        return getOrder(model);
    }


    @GetMapping("/order/addBook/{bookId}")
    public String addBookToOrder(@PathVariable("bookId") Long bookId, Model model) {
        orderService.AddBookToOrder(bookService.findBookById(bookId));
        return getOrder(model);
    }

    @GetMapping("/order/removeBook/{bookId}")
    public String removeBookFromOrder(@PathVariable("bookId") Long bookId, Model model) {
        orderService.removeBookFromOrder(bookService.findBookById(bookId));
        return getOrder(model);
    }

    @PostMapping("/order/updateBook")
    public String updateQuantity(@RequestParam(name = "bookId") Long bookId,
                                 @RequestParam(name = "quantity") Long quantity,
                                 Model model) {
        orderService.updateQuantity(bookService.findBookById(bookId), quantity);
        return getOrder(model);
    }
}
