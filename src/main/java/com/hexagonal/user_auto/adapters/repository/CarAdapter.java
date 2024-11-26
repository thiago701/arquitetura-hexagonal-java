package com.hexagonal.user_auto.adapters.repository;

import com.hexagonal.user_auto.adapters.repository.jpa.CarRepository;
import com.hexagonal.user_auto.adapters.repository.model.Car;
import com.hexagonal.user_auto.core.port.in.CarPort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CarAdapter implements CarPort {
    private final CarRepository carRepository;

    public CarAdapter(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @Transactional
    @Override
    public Car saveCar(Car car) {
        return carRepository.save(car);
    }

    @Override
    public Car updateCar(Car car) {
        return carRepository.save(car);
    }

    @Override
    public void deleteCar(Long id, String login) {
        carRepository.deleteByIdAndUserLogin(id, login);
    }

    @Override
    public Car findCarById(Long id) {
        return carRepository.findById(id).orElse(null);
    }

    @Override
    public List<Car> findAllCars() {
        return carRepository.findAll().stream().collect(Collectors.toList());
    }

    @Override
    public List<Car> findCarsByUserLogin(String login) {
        return carRepository.findCarsByUserLogin(login);
    }

    @Override
    public Car findCarByIdAndUserLogin(Long id, String login) {
        return carRepository.findByIdAndUserLogin(id, login);
    }

}
