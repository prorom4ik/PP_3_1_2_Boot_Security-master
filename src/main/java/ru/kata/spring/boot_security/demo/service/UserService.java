package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {
    User findByUsername(String name);
    User findById(Long id);
    List<User> findAll();
    void saveUser(User user);
    void deleteById(long id);
    void updateUser(Long id, User updatedUser);
}
