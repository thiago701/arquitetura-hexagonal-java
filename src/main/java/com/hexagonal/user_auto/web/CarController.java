package com.hexagonal.user_auto.web;

import com.hexagonal.user_auto.adapters.repository.model.Car;
import com.hexagonal.user_auto.adapters.repository.model.User;
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
@RequestMapping("/api/cars")
@Tag(name = "Car Controller", description = "Operações relacionadas aos carros")
public class CarController {

    private final CarUserCase carUserCase;
    private final CarUtilizationUserCase carUtilizationUserCase;

    public CarController(CarUserCase carService, CarUtilizationUserCase carUtilizationUserCase) {
        this.carUserCase = carService;
        this.carUtilizationUserCase = carUtilizationUserCase;
    }

    @PostMapping
    @Operation(summary = "Cadastrar um Novo Carro", description = "Permite ao usuário logado cadastrar um novo")
    public ResponseEntity<?> createCar(@RequestBody @Valid Car car, BindingResult bindingResult) {
        // Verifica se existem erros de validação
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getFieldErrors().stream()
                    .map(error -> String.format("Invalid fields: %s", error.getDefaultMessage()))
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(errors);
        }
        // recupera o usuario logado e atribui
        car.setUser(getUsuarioLogado());
        Car savedCar = carUserCase.saveCar(car);
        URI location = URI.create("/cars/" + savedCar.getId());
        return ResponseEntity.created(location).body(savedCar);
    }

    @PutMapping("/{id}")
    @Operation(summary = " Atualizar um Carro pelo ID", description = "Atualiza as informações de um carro específico.")
    public ResponseEntity<Car> updateCar(@PathVariable Long id, @RequestBody Car car) {
        // ID do usuário para garantir que será atualizado corretamente
        car.setId(id);
        // recupera o usuario logado e atribui
        car.setUser(getUsuarioLogado());
        return ResponseEntity.ok(carUserCase.updateCar(car));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remover um Carro pelo ID", description = "Remove um carro específico do usuário.")
    public ResponseEntity<Void> deleteCar(@PathVariable Long id) {
        carUserCase.deleteCar(id, getUsuarioLogado().getLogin());
        return ResponseEntity.noContent().build();
    }

    // INSCREMENTA UTILIZACAO DO CARRO (++)
    @GetMapping("/{id}")
    @Operation(summary = "Buscar um Carro Específico pelo ID e Incrementa uso", description = "Busca um carro específico do usuário e incrementa utilização.")
    public ResponseEntity<Car> getCarById(@PathVariable Long id) {
        Car car = carUtilizationUserCase.incrementCarUsage(id, getUsuarioLogado().getLogin());
        return ResponseEntity.ok(car);
    }

    @GetMapping
    @Operation(summary = "Listar todos os carros do usuário logado", description = "Este endpoint retorna todos os carros registrados do usuário autenticado.")
    public ResponseEntity<List<Car>> getUserCars() {
        // Busca apenas os carros do usuário logado
        List<Car> userCars = carUserCase.findCarsByUserLogin(getUsuarioLogado().getLogin());
        return ResponseEntity.ok(userCars);
    }

    private static User getUsuarioLogado() {
        // Recupera usuário autenticado
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (User) authentication.getPrincipal();
    }
}
