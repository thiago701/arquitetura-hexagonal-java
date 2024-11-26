package com.hexagonal.user_auto.web;

import com.hexagonal.user_auto.adapters.repository.model.User;
import com.hexagonal.user_auto.core.usercase.UserUserCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class UserControllerTest {

    @Mock
    private UserUserCase userUserCase;

    @Mock
    private BindingResult bindingResult;

    @InjectMocks
    private UserController userController;

    private User mockUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockUser = new User();
        mockUser.setId(1L);
        mockUser.setLogin("cafeino.cavalcanti");
        mockUser.setFirstName("Cafeino");
        mockUser.setLastName("Cavalcanti");
        mockUser.setLastLogin(LocalDateTime.now());
    }

    @Test
    void testCreateUserSuccess() {
        // Mock do BindingResult sem erros
        when(bindingResult.hasErrors()).thenReturn(false);

        when(userUserCase.saveUser(any(User.class))).thenReturn(mockUser);

        ResponseEntity<?> response = userController.createUser(mockUser, bindingResult);

        assertEquals(201, response.getStatusCodeValue());
        assertEquals(mockUser, response.getBody());
        assertEquals(URI.create("/users/1"), response.getHeaders().getLocation());
        verify(userUserCase, times(1)).saveUser(any(User.class));
    }

    @Test
    void testCreateUserValidationFailure() {
        // Mock do BindingResult com erro
        when(bindingResult.hasErrors()).thenReturn(true);
        when(bindingResult.getFieldErrors()).thenReturn(List.of());

        ResponseEntity<?> response = userController.createUser(mockUser, bindingResult);

        assertEquals(400, response.getStatusCodeValue());
        verify(userUserCase, never()).saveUser(any(User.class));
    }

    @Test
    void testUpdateUserSuccess() {
        when(userUserCase.updateUser(any(User.class))).thenReturn(mockUser);

        ResponseEntity<User> response = userController.updateUser(1L, mockUser);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(mockUser, response.getBody());
        verify(userUserCase, times(1)).updateUser(any(User.class));
    }

    @Test
    void testDeleteUserSuccess() {
        doNothing().when(userUserCase).deleteUser(anyLong());

        ResponseEntity<Void> response = userController.deleteUser(1L);

        assertEquals(204, response.getStatusCodeValue());
        verify(userUserCase, times(1)).deleteUser(anyLong());
    }

    @Test
    void testGetUserByIdSuccess() {
        when(userUserCase.findUserById(anyLong())).thenReturn(mockUser);

        ResponseEntity<User> response = userController.getUserById(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(mockUser, response.getBody());
        verify(userUserCase, times(1)).findUserById(anyLong());
    }

    @Test
    void testGetAllUsersSuccess() {
        User anotherUser = new User();
        anotherUser.setId(2L);
        anotherUser.setLogin("silvinha.italia");
        anotherUser.setFirstName("Silvinha");
        anotherUser.setLastName("Italia");

        when(userUserCase.findAllUsers()).thenReturn(List.of(mockUser, anotherUser));

        ResponseEntity<List<User>> response = userController.getAllUsers();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(2, response.getBody().size());
        verify(userUserCase, times(1)).findAllUsers();
    }
}
