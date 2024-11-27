package com.hexagonal.user_auto.core.usercase;

import com.hexagonal.user_auto.adapters.repository.jpa.CarUtilizationRepository;
import com.hexagonal.user_auto.adapters.repository.jpa.UserRepository;
import com.hexagonal.user_auto.adapters.repository.model.Car;
import com.hexagonal.user_auto.adapters.repository.model.CarUtilization;
import com.hexagonal.user_auto.adapters.repository.model.User;
import com.hexagonal.user_auto.core.port.in.CarPort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarUtilizationUserCase {

    private final CarPort carPort;
    private final CarUtilizationRepository carUtilizationRepository;
    private final UserRepository userRepository;

    public CarUtilizationUserCase(CarPort carPort,
                                  CarUtilizationRepository carUtilizationRepository,
                                  UserRepository userRepository) {
        this.carPort = carPort;
        this.carUtilizationRepository = carUtilizationRepository;
        this.userRepository = userRepository;
    }

    public Car incrementCarUsage(Long carId, String userLogin) {
        // Busca o carro pelo ID
        Car car = carPort.findCarById(carId);
        if (car == null) {
            throw new IllegalArgumentException("Carro não encontrado: " + carId);
        }

        // Busca o usuário pelo login
        Optional<User> userOpt = userRepository.findByLogin(userLogin);
        if (userOpt.isEmpty()) {
            throw new IllegalArgumentException("Usuário não encontrado: " + userLogin);
        }

        User user = userOpt.get();

        // Busca a contagem de uso do carro para o usuário ou cria um novo registro
        Optional<CarUtilization> carUsageOpt = carUtilizationRepository.findByCarAndUser(car, user);
        CarUtilization carUsage = carUsageOpt.orElseGet(() -> new CarUtilization(car, user, 0));

        // Incrementa o contador de utilização e salva
        carUsage.incrementUsage();
        carUtilizationRepository.save(carUsage);
        return car;
    }

    public List<CarUtilization> getAll() {
        return carUtilizationRepository.findAll();
    }
}
