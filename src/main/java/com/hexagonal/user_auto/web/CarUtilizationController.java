package com.hexagonal.user_auto.web;

import com.hexagonal.user_auto.adapters.repository.mapper.CarUtilizationMapper;
import com.hexagonal.user_auto.adapters.repository.model.Car;
import com.hexagonal.user_auto.adapters.repository.model.CarUtilization;
import com.hexagonal.user_auto.adapters.repository.model.User;
import com.hexagonal.user_auto.core.domain.model.dto.CarUtilizationResponse;
import com.hexagonal.user_auto.core.usercase.CarUserCase;
import com.hexagonal.user_auto.core.usercase.CarUtilizationUserCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/cars_utilization")
@Tag(name = "Car Controller", description = "Operações relacionadas ao ranking de usuários e seus carros")
public class CarUtilizationController {

    private final CarUtilizationUserCase carUtilizationUserCase;
    private final CarUtilizationMapper carUtilizationMapper;

    public CarUtilizationController(CarUtilizationUserCase carUtilizationUserCase, CarUtilizationMapper carUtilizationMapper) {
        this.carUtilizationUserCase = carUtilizationUserCase;
        this.carUtilizationMapper = carUtilizationMapper;
    }

    @GetMapping
    @Operation(summary = "Listar todos os carros utilizados", description = "Este endpoint retorna todos os carros registrados com utilização")
    public ResponseEntity<List<CarUtilizationResponse>> getCarsUtilization() {
        List<CarUtilization> carUtilizations = carUtilizationUserCase.getAll();

        List<CarUtilizationResponse> response = carUtilizations.stream()
                .map(carUtilizationMapper::toResponse)
                .toList();

        return ResponseEntity.ok(response);
    }

    private static User getUsuarioLogado() {
        // Recupera usuário autenticado
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (User) authentication.getPrincipal();
    }
}
