package com.thymeleaf.bookstore.controller;

import com.thymeleaf.bookstore.model.Order;
import com.thymeleaf.bookstore.model.User;
import com.thymeleaf.bookstore.security.SecurityConstants;
import com.thymeleaf.bookstore.service.OrderService;
import com.thymeleaf.bookstore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Objects;

@Controller
public class UserController {

    private final UserService userService;
    private final OrderService orderService;

    @Autowired
    public UserController(UserService userService, OrderService orderService) {
        this.userService = userService;
        this.orderService = orderService;
    }

    @PostMapping("/users")
    @Secured(SecurityConstants.ADMIN)
    public String createUser(@RequestBody User user, Model model) {
        if (user.getUserId() != null) {
            model.addAttribute("message", "User exists");
        } else if (userService.findByUserName(user.getUserName()) != null) {
            model.addAttribute("message", "Username exists");
        } else {
            userService.registerUser(user);
            model.addAttribute("message", "User created successfully");
        }

        return "editUser";
    }

    @GetMapping("/profile")
    public String getCurrentUser(Principal principal, Model model) {
        if (principal == null) {
            return "redirect:/home";
        }
        User user = userService.findByUserName(principal.getName());
        List<Order> orders = orderService.findByUserName(principal.getName());
        model.addAttribute("user", user);
        model.addAttribute("orders", orders);
        return "profile";
    }

    @PutMapping("/profile")
    public String updateUser(@ModelAttribute User user,
                             BindingResult bindingResult,
                             Model model,
                             Principal principal) {
        User userExists = userService.findByUserName(user.getUserName());
        if (userExists != null && !Objects.equals(userExists.getUserName(), principal.getName())){
            bindingResult.rejectValue("userName", "error.user",
                    "There is already a user registered with the username provided");
        }
        if (!bindingResult.hasErrors()) {
            userService.updateUser(user);
            model.addAttribute("successMessage", "User has been updated successfully");
        }
        return getCurrentUser(principal, model);
    }
}
