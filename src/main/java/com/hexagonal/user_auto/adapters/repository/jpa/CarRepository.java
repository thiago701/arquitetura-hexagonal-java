package com.hexagonal.user_auto.adapters.repository.jpa;

import com.hexagonal.user_auto.adapters.repository.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface CarRepository extends JpaRepository<Car,Long> {

    List<Car> findCarsByUserLogin(String login);
    Car findByIdAndUserLogin(Long id, String login);
    void deleteByIdAndUserLogin(Long id, String login);
}
