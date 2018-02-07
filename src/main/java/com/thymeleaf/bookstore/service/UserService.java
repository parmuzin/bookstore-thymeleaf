package com.thymeleaf.bookstore.service;

import com.thymeleaf.bookstore.form.UserForm;
import com.thymeleaf.bookstore.model.User;

import java.util.List;

public interface UserService {

    List<User> findAllUsers();

    User findUserById(Long userId);

    User findByUserName(String userName);

    User registerUser(User user);

    User createUser(UserForm userForm);

    void updateUser(User user);

    void deleteUserById(Long userId);
}
