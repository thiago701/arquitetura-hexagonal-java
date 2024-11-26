package com.hexagonal.user_auto.core.port.in;

import com.hexagonal.user_auto.adapters.repository.model.Car;

import java.util.List;

public interface CarPort {
    Car saveCar(Car car);
    Car updateCar(Car car);
    void deleteCar(Long id, String login);
    Car findCarById(Long id);
    List<Car> findAllCars();
    List<Car> findCarsByUserLogin(String login);
    Car findCarByIdAndUserLogin(Long id, String login);
}
