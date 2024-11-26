package com.hexagonal.user_auto.adapters.repository.jpa;

import com.hexagonal.user_auto.adapters.repository.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.nio.channels.FileChannel;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByLogin(String username);

    boolean existsByEmail(String email);

    boolean existsByLogin(String login);
}
