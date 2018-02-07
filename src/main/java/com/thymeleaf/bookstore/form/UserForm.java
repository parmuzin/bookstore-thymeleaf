package com.thymeleaf.bookstore.form;

import com.thymeleaf.bookstore.model.Authority;
import com.thymeleaf.bookstore.model.User;

import java.util.HashSet;
import java.util.Set;

public class UserForm {

    private Long userId;

    private String userName;

    private String userPassword;

    private String userFirstName;

    private String userLastName;

    private Set<Authority> authorities = new HashSet<>();


    public UserForm() {
    }

    public UserForm(User user) {
        this.userId = user.getUserId();
        this.userName = user.getUserName();
        this.userPassword = user.getUserPassword();
        this.userFirstName = user.getUserFirstName();
        this.userLastName = user.getUserLastName();
        this.authorities = user.getAuthorities();
    }

    public User toUser() {
        User user = new User();
        user.setUserId(userId);
        user.setUserName(userName);
        user.setUserPassword(userPassword);
        user.setUserFirstName(userFirstName);
        user.setUserLastName(userLastName);
        user.setAuthorities(authorities);

        return user;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }

    public Set<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
    }
}
