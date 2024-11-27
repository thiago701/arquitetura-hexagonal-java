package com.hexagonal.user_auto.web;

import com.hexagonal.user_auto.adapters.repository.model.Car;
import com.hexagonal.user_auto.core.usercase.UserUserCase;
import com.hexagonal.user_auto.adapters.repository.model.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = {"http://localhost:4200", "https://user-auto-front-dac15affdc70.herokuapp.com"})
@RestController
@RequestMapping("/api/users")
@Tag(name = "User Controller", description = "Operações relacionadas aos usuarios")
public class UserController {

    private final UserUserCase userUserCase;

    public UserController(UserUserCase userUserCase) {
        this.userUserCase = userUserCase;
    }

    // Criar um novo usuário
    @PostMapping
    @Operation(summary = "Cadastrar um Novo Usuário", description = "Permite o cadastro de um novo usuário no sistema.")
    public ResponseEntity<?> createUser(@RequestBody @Valid User user, BindingResult bindingResult) {
        // Verifica se existem erros de validação
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getFieldErrors().stream()
                    .map(error -> String.format("Invalid fields: %s", error.getDefaultMessage()))
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(errors);
        }
        // Salva
        User savedUser = userUserCase.saveUser(user);
        URI location = URI.create("/users/" + savedUser.getId());
        return ResponseEntity.created(location).body(savedUser);
    }


    // Atualizar um usuário existente
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar um Usuário pelo ID", description = "Atualiza as informações de um usuário específico.")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        user.setId(id); // ID do usuário para garantir que será atualizado corretamente
        return ResponseEntity.ok(userUserCase.updateUser(user));
    }

    // Deletar um usuário pelo ID
    @DeleteMapping("/{id}")
    @Operation(summary = "Remover um Usuário pelo ID", description = " Remove um usuário específico do sistema.")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userUserCase.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    // Obter um usuário por ID
    @GetMapping("/{id}")
    @Operation(summary = "Buscar um Usuário pelo ID", description = "Retorna as informações de um usuário específico.")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userUserCase.findUserById(id));
    }

    // Obter todos os usuários
    @GetMapping
    @Operation(summary = "Listar Todos os Usuários", description = "Retorna todos os usuários cadastrados no sistema.")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userUserCase.findAllUsers());
    }
}
