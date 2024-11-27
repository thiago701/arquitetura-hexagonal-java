package com.hexagonal.user_auto.adapters.repository.mapper;

import com.hexagonal.user_auto.adapters.repository.model.CarUtilization;
import com.hexagonal.user_auto.core.domain.model.dto.CarUtilizationResponse;
import org.springframework.stereotype.Component;

@Component
public class CarUtilizationMapper {

    public CarUtilizationResponse toResponse(CarUtilization carUti) {
        return new CarUtilizationResponse(
                carUti.getUser().getFirstName(),
                carUti.getCar().getModel(),
                carUti.getUtilizationCount()
        );
    }
}
