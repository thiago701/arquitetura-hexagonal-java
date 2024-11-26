package com.hexagonal.user_auto.core.port.in;

import com.hexagonal.user_auto.adapters.repository.model.User;

import java.util.List;
import java.util.Optional;

public interface UserPort {
    User saveUser(User user);
    User updateUser(User user);
    void deleteUser(Long id);
    User findUserById(Long id);
    List<User> findAllUsers();
    Optional<User> findUserByLogin(String login);
}
