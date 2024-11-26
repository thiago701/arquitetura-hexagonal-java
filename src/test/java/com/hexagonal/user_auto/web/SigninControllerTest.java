package com.hexagonal.user_auto.web;

import com.hexagonal.user_auto.adapters.repository.model.User;
import com.hexagonal.user_auto.config.JwtTokenUtil;
import com.hexagonal.user_auto.core.domain.exception.ApiException;
import com.hexagonal.user_auto.core.domain.model.dto.SigninRequest;
import com.hexagonal.user_auto.core.usercase.UserUserCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class SigninControllerTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtTokenUtil jwtTokenUtil;

    @Mock
    private UserUserCase userUserCase;

    @Mock
    private Authentication authentication;

    @Mock
    private UserDetails userDetails;

    @InjectMocks
    private SigninController signinController;

    private User mockUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockUser = new User();
        mockUser.setId(1L);
        mockUser.setLogin("carlos.santos");
        mockUser.setFirstName("Carlos");
        mockUser.setLastName("Santos");
        mockUser.setLastLogin(LocalDateTime.now());
    }

    @Test
    void testAuthenticateSuccess() {
        // Arrange
        SigninRequest signinRequest = new SigninRequest("joaquina", "12345");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(mockUser);
        when(jwtTokenUtil.generateToken(anyString())).thenReturn("dummyToken");

        // Act
        Map<String, String> response = signinController.authenticate(signinRequest);

        // Assert
        assertNotNull(response);
        assertTrue(response.containsKey("token"));
        assertEquals("dummyToken", response.get("token"));

        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtTokenUtil, times(1)).generateToken(anyString());
        verify(userUserCase, times(1)).updateUser(any(User.class));
    }

    @Test
    void testAuthenticateBadCredentials() {
        // Arrange
        SigninRequest signinRequest = new SigninRequest("invalidUser", "wrongPassword");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new BadCredentialsException("Bad credentials"));

        // Act & Assert
        ApiException exception = assertThrows(ApiException.class, () -> {
            signinController.authenticate(signinRequest);
        });

        assertEquals("Invalid login or password", exception.getMessage());
        assertEquals(HttpStatus.UNAUTHORIZED.value(), exception.getStatusErrorCode());

        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtTokenUtil, never()).generateToken(anyString());
        verify(userUserCase, never()).updateUser(any(User.class));
    }

    @Test
    void testAuthenticateUnexpectedError() {
        // Arrange
        SigninRequest signinRequest = new SigninRequest("john.gomes", "12345");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new RuntimeException("Unexpected error"));

        // Act & Assert
        ApiException exception = assertThrows(ApiException.class, () -> {
            signinController.authenticate(signinRequest);
        });

        assertEquals("Authentication failed due to server error", exception.getMessage());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), exception.getStatusErrorCode());

        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtTokenUtil, never()).generateToken(anyString());
        verify(userUserCase, never()).updateUser(any(User.class));
    }

    @Test
    void testUpdateLastLoginAfterSuccessfulAuthentication() {
        // Arrange
        SigninRequest signinRequest = new SigninRequest("gilmara", "12345");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(mockUser);
        when(jwtTokenUtil.generateToken(anyString())).thenReturn("dummyToken");

        // Act
        signinController.authenticate(signinRequest);

        // Assert
        verify(userUserCase, times(1)).updateUser(any(User.class));
        assertNotNull(mockUser.getLastLogin());
    }
}
