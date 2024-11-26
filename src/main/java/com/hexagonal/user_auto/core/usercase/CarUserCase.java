package com.hexagonal.user_auto.core.usercase;

import com.hexagonal.user_auto.adapters.repository.model.Car;
import com.hexagonal.user_auto.core.port.in.CarPort;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarUserCase {
    private final CarPort carPort;

    public CarUserCase(CarPort carPort) {
        this.carPort = carPort;
    }

    // Salvar um novo carro
    public Car saveCar(Car car) {
        return carPort.saveCar(car);
    }

    // Atualizar as informações de um carro existente
    public Car updateCar(Car car) {
        return carPort.updateCar(car);
    }

    // Deletar um carro com base no ID
    public void deleteCar(Long id, String login) {
        carPort.deleteCar(id, login);
    }

    // Buscar um carro por ID
    public Car findCarById(Long id) {
        return carPort.findCarById(id);
    }

    // Listar todos os carros
    public List<Car> findAllCars() {
        return carPort.findAllCars();
    }

    // Listar todos os carros por usuario logado
    public List<Car> findCarsByUserLogin(String login) { return carPort.findCarsByUserLogin(login); }

    // Buscar carro por ID e usuario logado
    public Car findCarByIdAndUserLogin(Long id, String login) { return carPort.findCarByIdAndUserLogin(id, login); }
}
