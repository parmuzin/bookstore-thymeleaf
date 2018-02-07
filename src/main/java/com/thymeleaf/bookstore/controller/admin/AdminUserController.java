package com.thymeleaf.bookstore.controller.admin;

import com.thymeleaf.bookstore.model.Order;
import com.thymeleaf.bookstore.model.User;
import com.thymeleaf.bookstore.service.OrderService;
import com.thymeleaf.bookstore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("admin")
public class AdminUserController {

    private final UserService userService;

    private final OrderService orderService;

    @Autowired
    public AdminUserController(UserService userService, OrderService orderService) {
        this.userService = userService;
        this.orderService = orderService;
    }

    @GetMapping("users")
    public String getUsers(Model model) {
        List<User> users = userService.findAllUsers();
        model.addAttribute("users", users);
        return "/admin/users";
    }

    @GetMapping("/users/add")
    public String addUserPage(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "/admin/addUser";
    }

    @PostMapping("/users/add")
    public String addUser(@ModelAttribute User user, Model model, BindingResult bindingResult) {
        User userExists = userService.findByUserName(user.getUserName());
        if (userExists != null) {
            bindingResult.rejectValue("userName", "error.user",
                    "There is already a user registered with the username provided");
        }
        if (!bindingResult.hasErrors()) {
            User newUser = userService.registerUser(user);
            model.addAttribute("successMessage", "User has been created with ID " + newUser.getUserId());
            model.addAttribute("user", new User());
        }
        return "/admin/addUser";
    }

    @GetMapping("/users/edit/{userId}")
    public String editUserPage(@PathVariable Long userId, Model model) {
        User user = userService.findUserById(userId);
        List<Order> orders = orderService.findByUserName(user.getUserName());
        model.addAttribute("user", user);
        model.addAttribute("orders", orders);
        model.addAttribute("user", user);
        return "/admin/editUser";
    }

    @PostMapping("/users/edit")
    public String editUser(@ModelAttribute User user, Model model) {
        userService.updateUser(user);
        model.addAttribute("successMessage", "User updated with ID " + user.getUserId());
        return editUserPage(user.getUserId(), model);
    }

    @GetMapping("/users/delete/{userId}")
    public String deleteUser(@PathVariable Long userId, Model model) {
        userService.deleteUserById(userId);
        model.addAttribute("successMessage", "User with id " + userId + " deleted successfully");
        return getUsers(model);
    }

    @GetMapping("/users/edit/{userId}/completeOrder/{orderId}")
    public String completeOrder(@PathVariable Long orderId, @PathVariable Long userId, Model model) {
        Order order = orderService.findOrderById(orderId);
        order.setOrderStatus(true);
        orderService.updateOrder(order);
        model.addAttribute("successMessage", "Order with id " + orderId + " completed successfully");
        return editUserPage(userId, model);
    }

    @GetMapping("/users/edit/{userId}/deleteOrder/{orderId}")
    public String deleteOrder(@PathVariable Long orderId, @PathVariable Long userId, Model model) {
        Order order = orderService.findOrderById(orderId);
        orderService.deleteOrder(order);
        model.addAttribute("successMessage", "Order with id " + orderId + " deleted successfully");
        return editUserPage(userId, model);
    }
}
