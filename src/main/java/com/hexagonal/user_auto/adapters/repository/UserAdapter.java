package com.hexagonal.user_auto.adapters.repository;

import com.hexagonal.user_auto.adapters.repository.jpa.CarRepository;
import com.hexagonal.user_auto.adapters.repository.jpa.UserRepository;
import com.hexagonal.user_auto.adapters.repository.model.Car;
import com.hexagonal.user_auto.adapters.repository.model.User;
import com.hexagonal.user_auto.core.domain.exception.ApiException;
import com.hexagonal.user_auto.core.port.in.UserPort;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class UserAdapter implements UserPort {

    private final UserRepository userRepository;
    private final CarRepository carRepository;
    private PasswordEncoder passwordEncoder;

    public UserAdapter(UserRepository userRepository,
                       CarRepository carRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.carRepository = carRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    @Override
    public User saveUser(User user) {
        if (emailExists(user.getEmail())) {
            throw new ApiException("Email already exists", HttpStatus.CONFLICT);
        }

        if (loginExists(user.getLogin())) {
            throw new ApiException("Login already exists", HttpStatus.CONFLICT);
        }
        for (Car car : user.getCars()) {
            car.setUser(user);
        }
        // crypt password
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User updateUser(User user) {
        // crypt password
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User findUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public List<User> findAllUsers() {
        return new ArrayList<>(userRepository.findAll());
    }

    @Override
    public Optional<User> findUserByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    public boolean emailExists(String email) {
        return userRepository.existsByEmail(email);
    }

    public boolean loginExists(String login) {
        return userRepository.existsByLogin(login);
    }
}
