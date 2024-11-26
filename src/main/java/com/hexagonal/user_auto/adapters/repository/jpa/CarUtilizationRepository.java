package com.hexagonal.user_auto.adapters.repository.jpa;

import com.hexagonal.user_auto.adapters.repository.model.Car;
import com.hexagonal.user_auto.adapters.repository.model.CarUtilization;
import com.hexagonal.user_auto.adapters.repository.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public interface CarUtilizationRepository extends JpaRepository<CarUtilization,Long> {
    Optional<CarUtilization> findByCarAndUser(Car car, User user);
    List<CarUtilization> findByUser(User user);
}
