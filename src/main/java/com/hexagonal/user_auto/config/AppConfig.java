package com.hexagonal.user_auto.config;

import com.hexagonal.user_auto.adapters.repository.CarAdapter;

import com.hexagonal.user_auto.core.usercase.CarUserCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public CarUserCase carService(CarAdapter carAdapter) {
        return new CarUserCase(carAdapter);
    }
}
