package com.hexagonal.user_auto.web;

import com.hexagonal.user_auto.adapters.repository.model.Car;
import com.hexagonal.user_auto.adapters.repository.model.User;
import com.hexagonal.user_auto.core.usercase.UserCarRankingUserCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rankings")
@Tag(name = "User Ranking Controller", description = "Operações relacionadas ao ranking de usuários e seus carros")
public class UserRankingController {

    @Autowired
    private UserCarRankingUserCase userCarRankingUserCase;

    @GetMapping("/users")
    @Operation(summary = "Lista ranking dos usuarios", description = "Retorna a lista de usuários ordenados pela utilização de seus carros")
    public ResponseEntity<List<User>> getRankedUsers() {
        List<User> rankedUsers = userCarRankingUserCase.getRankedUsersWithCars();
        return ResponseEntity.ok(rankedUsers);
    }

    @GetMapping("/users/{userId}/cars")
    @Operation(summary = "Lista ranking de carros por usuario", description = "Retorna a lista de carros de um usuário ordenados pela quantidade de utilização")
    public ResponseEntity<List<Car>> getRankedCarsForUser(@PathVariable Long userId) {
        User user = new User();
        user.setId(userId);

        List<Car> rankedCars = userCarRankingUserCase.getRankedCarsForUser(user.getId());
        return ResponseEntity.ok(rankedCars);
    }
}
