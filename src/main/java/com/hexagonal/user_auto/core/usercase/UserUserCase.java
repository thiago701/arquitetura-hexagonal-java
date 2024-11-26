package com.hexagonal.user_auto.core.usercase;

import com.hexagonal.user_auto.adapters.repository.model.Car;
import com.hexagonal.user_auto.adapters.repository.model.User;
import com.hexagonal.user_auto.core.port.in.UserPort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserUserCase {
    private final UserPort userPort;

    public UserUserCase(UserPort userPort) {
        this.userPort = userPort;
    }

    // Salvar um novo usuário
    public User saveUser(User user) {
        return userPort.saveUser(user);
    }

    // Atualizar as informações de um usuário existente
    public User updateUser(User user) {
        for(Car c : user.getCars()) {
            c.setUser(user);
        }
        return userPort.updateUser(user);
    }

    // Deletar um usuário com base no ID
    public void deleteUser(Long id) {
        userPort.deleteUser(id);
    }

    // Encontrar um usuário por ID
    public User findUserById(Long id) {
        return userPort.findUserById(id);
    }

    // Encontrar um usuário por login
    public Optional<User> findUserByLogin(String login) {
        return userPort.findUserByLogin(login);
    }

    // Encontrar todos os usuários
    public List<User> findAllUsers() {
        return userPort.findAllUsers();
    }
}
