package com.thymeleaf.bookstore.service.impl;

import com.thymeleaf.bookstore.form.UserForm;
import com.thymeleaf.bookstore.model.Authority;
import com.thymeleaf.bookstore.model.User;
import com.thymeleaf.bookstore.repository.AuthorityRepository;
import com.thymeleaf.bookstore.repository.UserRepository;
import com.thymeleaf.bookstore.security.SecurityConstants;
import com.thymeleaf.bookstore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           AuthorityRepository authorityRepository,
                           BCryptPasswordEncoder bCryptPasswordEncoder) {

        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public User findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    @Override
    public User registerUser(User user) {
        User newUser = new User();
        Authority authority = authorityRepository.findOne(SecurityConstants.USER);
        Set<Authority> authorities = new HashSet<>();
        String encryptedPassword = bCryptPasswordEncoder.encode(user.getUserPassword());
        newUser.setUserName(user.getUserName());
        newUser.setUserPassword(encryptedPassword);
        newUser.setUserFirstName(user.getUserFirstName());
        newUser.setUserLastName(user.getUserLastName());
        authorities.add(authority);
        newUser.setAuthorities(authorities);
        userRepository.save(newUser);
        return newUser;
    }

    @Override
    public User createUser(UserForm userForm) {
        User user = userForm.toUser();
        return user;
    }

    @Override
    public void updateUser(User user) {
        User updatedUser = userRepository.findOne(user.getUserId());
        updatedUser.setUserName(user.getUserName());
        updatedUser.setUserFirstName(user.getUserFirstName());
        updatedUser.setUserLastName(user.getUserLastName());
        if (!user.getUserPassword().isEmpty()) {
            String encryptedPassword = bCryptPasswordEncoder.encode(user.getUserPassword());
            updatedUser.setUserPassword(encryptedPassword);
        }
        userRepository.save(updatedUser);
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User findUserById(Long userId) {
        return userRepository.findOne(userId);
    }

    @Override
    public void deleteUserById(Long userId) {
        userRepository.delete(userId);
    }
}
