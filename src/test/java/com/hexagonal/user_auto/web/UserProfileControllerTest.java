package com.hexagonal.user_auto.web;

import com.hexagonal.user_auto.adapters.repository.model.Car;
import com.hexagonal.user_auto.adapters.repository.model.User;
import com.hexagonal.user_auto.config.JwtTokenUtil;
import com.hexagonal.user_auto.core.domain.exception.ApiException;
import com.hexagonal.user_auto.core.domain.model.dto.UserProfileResponse;
import com.hexagonal.user_auto.core.usercase.UserUserCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class UserProfileControllerTest {

    @Mock
    private JwtTokenUtil jwtTokenUtil;

    @Mock
    private UserDetailsService userDetailsService;

    @Mock
    private UserUserCase userUserCase;

    @InjectMocks
    private UserProfileController userProfileController;

    private User mockUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockUser = new User();
        mockUser.setId(1L);
        mockUser.setLogin("dantas.amoroso");
        mockUser.setFirstName("Dantas");
        mockUser.setLastName("Amoroso");
        mockUser.setEmail("damoroso@mail.com");
        mockUser.setBirthday("1990-10-10");
        mockUser.setPhone("123456789");
        mockUser.setCreatedAt(LocalDate.now());
        mockUser.setLastLogin(LocalDateTime.now());

        Car car1 = new Car(1L, 2015, "XYZ-1234", "Corolla", "Branco", LocalDate.now(), mockUser);
        Car car2 = new Car(2L, 2018, "ABC-5678", "Civic", "Preto", LocalDate.now(), mockUser);

        mockUser.setCars(List.of(car1, car2));
    }

    @Test
    void testGetUserProfileSuccess() {
        // Arrange
        String token = "Bearer validToken";
        when(jwtTokenUtil.extractUsername(anyString())).thenReturn("testUser");
        when(jwtTokenUtil.validateToken(anyString(), anyString())).thenReturn(true);
        when(userDetailsService.loadUserByUsername(anyString())).thenReturn(mockUser);
        when(userUserCase.findUserByLogin(anyString())).thenReturn(Optional.of(mockUser));

        // Act
        UserProfileResponse response = userProfileController.getUserProfile(token);

        // Assert
        assertEquals(mockUser.getFirstName(), response.getFirstName());
        assertEquals(mockUser.getLastName(), response.getLastName());
        assertEquals(mockUser.getEmail(), response.getEmail());
        assertEquals(mockUser.getLogin(), response.getLogin());
        assertEquals(mockUser.getCars().size(), response.getCars().size());
        verify(jwtTokenUtil, times(1)).validateToken(anyString(), anyString());
        verify(userUserCase, times(1)).findUserByLogin(anyString());
    }

    @Test
    void testGetUserProfileUnauthorizedMissingToken() {
        // Arrange
        String token = null;

        // Act & Assert
        ApiException exception = assertThrows(ApiException.class, () -> {
            userProfileController.getUserProfile(token);
        });

        assertEquals("Unauthorized", exception.getMessage());
        assertEquals(HttpStatus.UNAUTHORIZED.value(), exception.getStatusErrorCode());

        verify(jwtTokenUtil, never()).validateToken(anyString(), anyString());
        verify(userUserCase, never()).findUserByLogin(anyString());
    }

    @Test
    void testGetUserProfileUnauthorizedInvalidToken() {
        // Arrange
        String token = "Bearer invalidToken";
        when(jwtTokenUtil.extractUsername(anyString())).thenThrow(new RuntimeException("Invalid token"));

        // Act & Assert
        ApiException exception = assertThrows(ApiException.class, () -> {
            userProfileController.getUserProfile(token);
        });

        assertEquals("Unauthorized - invalid session", exception.getMessage());
        assertEquals(HttpStatus.UNAUTHORIZED.value(), exception.getStatusErrorCode());

        verify(jwtTokenUtil, times(1)).extractUsername(anyString());
        verify(jwtTokenUtil, never()).validateToken(anyString(), anyString());
        verify(userUserCase, never()).findUserByLogin(anyString());
    }

    @Test
    void testGetUserProfileUnauthorizedInvalidSession() {
        // Arrange
        String token = "Bearer validToken";
        when(jwtTokenUtil.extractUsername(anyString())).thenReturn("carol.loina");
        when(jwtTokenUtil.validateToken(anyString(), anyString())).thenReturn(false);
        when(userDetailsService.loadUserByUsername(anyString())).thenReturn(mockUser);

        // Act & Assert
        ApiException exception = assertThrows(ApiException.class, () -> {
            userProfileController.getUserProfile(token);
        });

        assertEquals("Unauthorized - invalid session", exception.getMessage());
        assertEquals(HttpStatus.UNAUTHORIZED.value(), exception.getStatusErrorCode());

        verify(jwtTokenUtil, times(1)).validateToken(anyString(), anyString());
        verify(userUserCase, never()).findUserByLogin(anyString());
    }
}
