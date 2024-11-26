package com.hexagonal.user_auto.core.usercase;

import com.hexagonal.user_auto.adapters.repository.jpa.CarUtilizationRepository;
import com.hexagonal.user_auto.adapters.repository.jpa.UserRepository;
import com.hexagonal.user_auto.adapters.repository.model.Car;
import com.hexagonal.user_auto.adapters.repository.model.CarUtilization;
import com.hexagonal.user_auto.adapters.repository.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserCarRankingUserCase {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CarUtilizationRepository carUtilizationRepository;

    public List<User> getRankedUsersWithCars() {
        List<User> users = userRepository.findAll();

        // Ordenar os usuários pela soma total de utilizações dos carros
        return users.stream()
                .sorted(Comparator.comparingInt(this::getTotalUsage).reversed()
                        .thenComparing(User::getLogin))
                .collect(Collectors.toList());
    }

    private int getTotalUsage(User user) {
        return carUtilizationRepository.findByUser(user).stream()
                .mapToInt(CarUtilization::getUtilizationCount)
                .sum();
    }

    public List<Car> getRankedCarsForUser(Long userId) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            throw new IllegalArgumentException("Usuário não encontrado: " + userId);
        }

        User user = userOpt.get();
        return carUtilizationRepository.findByUser(user).stream()
                .sorted(Comparator.comparingInt(CarUtilization::getUtilizationCount).reversed()
                        .thenComparing(carUsage -> carUsage.getCar().getModel()))
                .map(CarUtilization::getCar)
                .collect(Collectors.toList());
    }
}
