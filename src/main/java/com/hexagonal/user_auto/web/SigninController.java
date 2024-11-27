package com.hexagonal.user_auto.web;

import com.hexagonal.user_auto.adapters.repository.model.User;
import com.hexagonal.user_auto.config.JwtTokenUtil;
import com.hexagonal.user_auto.core.domain.exception.ApiException;
import com.hexagonal.user_auto.core.domain.model.dto.SigninRequest;
import com.hexagonal.user_auto.core.usercase.UserUserCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200")
@Slf4j
@RestController
@RequestMapping("/api/signin")
@Tag(name = "Signin Controller", description = "Operações relacionadas ao login")
public class SigninController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserUserCase userUserCase;

    public SigninController(AuthenticationManager authenticationManager,
                            JwtTokenUtil jwtTokenUtil,
                            UserUserCase userUserCase) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userUserCase = userUserCase;
    }

    @PostMapping
    @Operation(summary = "Login (Signin)", description = "Realiza o login do usuário e retorna um token JWT.")
    public Map<String, String> authenticate(@Valid @RequestBody SigninRequest authRequest) {
        try {

            // Autentica o usuário com as credenciais informadas
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authRequest.getUsername(),
                            authRequest.getPassword()
                    )
            );

            // Gera o token JWT após autenticação bem-sucedida
            String token = jwtTokenUtil.generateToken(((UserDetails) auth.getPrincipal()).getUsername());

            // Atualizar o campo `lastLogin` no banco de dados
            User user = (User) auth.getPrincipal();
            //user.setId(user.getId());
            user.setLastLogin(LocalDateTime.now());
            userUserCase.updateUser(user);

            log.info("O usuário '{}' realizou login com sucesso.", authRequest.getUsername());
            return Map.of("token", token);

        } catch (BadCredentialsException e) {
            log.warn("Falha de autenticação para usuário '{}'", authRequest.getUsername());
            throw new ApiException("Invalid login or password", HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            log.error("Erro inesperado ao autenticar usuário: {}", e.getMessage());
            throw new ApiException("Authentication failed due to server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
