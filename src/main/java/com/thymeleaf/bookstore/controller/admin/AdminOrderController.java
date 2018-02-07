package com.thymeleaf.bookstore.controller.admin;

import com.thymeleaf.bookstore.model.Order;
import com.thymeleaf.bookstore.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("admin")
public class AdminOrderController {

    private final OrderService orderService;

    @Autowired
    public AdminOrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/orders/all")
    public String getAllOrders(Model model) {
        List<Order> orders = orderService.findAllOrders();
        model.addAttribute("orders", orders);
        model.addAttribute("showAllOrders", true);
        return "/admin/orders";
    }

    @GetMapping("/orders")
    public String getOrders(Model model) {
        List<Order> orders = orderService.findByOrderStatus(false);
        model.addAttribute("orders", orders);
        return "/admin/orders";
    }

    @GetMapping("/orders/complete/{orderId}")
    public String completeOrder(@PathVariable Long orderId, Model model) {
        Order order = orderService.findOrderById(orderId);
        order.setOrderStatus(true);
        orderService.updateOrder(order);
        model.addAttribute("successMessage", "Order with id " + orderId + " completed successfully");
        return getOrders(model);
    }

    @GetMapping("/orders/delete/{orderId}")
    public String deleteOrder(@PathVariable Long orderId, Model model) {
        Order order = orderService.findOrderById(orderId);
        orderService.deleteOrder(order);
        model.addAttribute("successMessage", "Order with id " + orderId + " deleted successfully");
        return getOrders(model);
    }
}
