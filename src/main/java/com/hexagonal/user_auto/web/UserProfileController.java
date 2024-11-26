package com.hexagonal.user_auto.web;

import com.hexagonal.user_auto.adapters.repository.model.User;
import com.hexagonal.user_auto.core.domain.exception.ApiException;
import com.hexagonal.user_auto.config.JwtTokenUtil;
import com.hexagonal.user_auto.core.domain.model.dto.UserProfileResponse;
import com.hexagonal.user_auto.core.usercase.UserUserCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@Tag(name = "User Profile Controller", description = "Operações relacionadas ao perfil de usuarios")
public class UserProfileController {

    private final JwtTokenUtil jwtTokenUtil;
    private final UserDetailsService userDetailsService;
    private final UserUserCase userUserCase;

    public UserProfileController(JwtTokenUtil jwtTokenUtil,
                                 UserDetailsService userDetailsService,
                                 UserUserCase userUserCase) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDetailsService = userDetailsService;
        this.userUserCase = userUserCase;
    }

    @GetMapping("/me")
    @Operation(summary = "Retornar Informações do Usuário Logado", description = "Retorna as informações do usuário atualmente autenticado.")
    public UserProfileResponse getUserProfile(@RequestHeader(value = "Authorization", required = false) String authorizationHeader) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new ApiException("Unauthorized", HttpStatus.UNAUTHORIZED);
        }

        String token = authorizationHeader.substring(7);
        String login;

        try {
            login = jwtTokenUtil.extractUsername(token);
        } catch (Exception e) {
            throw new ApiException("Unauthorized - invalid session", HttpStatus.UNAUTHORIZED);
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(login);
        if (!jwtTokenUtil.validateToken(token, userDetails.getUsername())) {
            throw new ApiException("Unauthorized - invalid session", HttpStatus.UNAUTHORIZED);
        }

        // Busca usuario
        Optional<User> user = userUserCase.findUserByLogin(login);

        return new UserProfileResponse(
                user.get().getFirstName(),
                user.get().getLastName(),
                user.get().getEmail(),
                user.get().getBirthday(),
                user.get().getLogin(),
                user.get().getPhone(),
                user.get().getCars(),
                user.get().getCreatedAt(),
                user.get().getLastLogin()
        );
    }

}
