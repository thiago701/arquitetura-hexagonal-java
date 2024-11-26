package com.hexagonal.user_auto.core.usercase;

import com.hexagonal.user_auto.adapters.repository.model.Car;
import com.hexagonal.user_auto.adapters.repository.model.User;
import com.hexagonal.user_auto.core.port.in.UserPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class UserUserCaseTest {

    @Mock
    private UserPort userPort;

    @InjectMocks
    private UserUserCase userUserCase;

    private User mockUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockUser = new User();
        mockUser.setId(1L);
        mockUser.setLogin("joao.nunes");
        mockUser.setFirstName("João");
        mockUser.setLastName("Nunes");
        mockUser.setEmail("joaon@gmail.com");
        mockUser.setBirthday("1990-01-02");
        mockUser.setPhone("123456789");
        mockUser.setCreatedAt(LocalDate.now());
        mockUser.setLastLogin(LocalDateTime.now());

        Car car1 = new Car(1L, 2015, "XYZ-1234", "Corolla", "Branco", LocalDate.now(), mockUser);
        Car car2 = new Car(2L, 2018, "ABC-5678", "Civic", "Preto", LocalDate.now(), mockUser);

        mockUser.setCars(List.of(car1, car2));
    }

    @Test
    void testSaveUser() {
        // Arrange
        when(userPort.saveUser(any(User.class))).thenReturn(mockUser);

        // Act
        User savedUser = userUserCase.saveUser(mockUser);

        // Assert
        assertNotNull(savedUser);
        assertEquals(mockUser.getId(), savedUser.getId());
        assertEquals(mockUser.getLogin(), savedUser.getLogin());
        verify(userPort, times(1)).saveUser(any(User.class));
    }

    @Test
    void testUpdateUser() {
        // Arrange
        when(userPort.updateUser(any(User.class))).thenReturn(mockUser);

        // Act
        User updatedUser = userUserCase.updateUser(mockUser);

        // Assert
        assertNotNull(updatedUser);
        assertEquals(mockUser.getId(), updatedUser.getId());
        assertEquals(mockUser.getLogin(), updatedUser.getLogin());
        verify(userPort, times(1)).updateUser(any(User.class));
    }

    @Test
    void testUpdateUserCarRelationship() {
        // Arrange
        when(userPort.updateUser(any(User.class))).thenReturn(mockUser);

        // Act
        User updatedUser = userUserCase.updateUser(mockUser);

        // Assert
        for (Car car : updatedUser.getCars()) {
            assertEquals(mockUser, car.getUser());
        }
        verify(userPort, times(1)).updateUser(any(User.class));
    }

    @Test
    void testDeleteUser() {
        // Act
        userUserCase.deleteUser(1L);

        // Assert
        verify(userPort, times(1)).deleteUser(anyLong());
    }

    @Test
    void testFindUserById() {
        // Arrange
        when(userPort.findUserById(anyLong())).thenReturn(mockUser);

        // Act
        User user = userUserCase.findUserById(1L);

        // Assert
        assertNotNull(user);
        assertEquals(mockUser.getId(), user.getId());
        assertEquals(mockUser.getLogin(), user.getLogin());
        verify(userPort, times(1)).findUserById(anyLong());
    }

    @Test
    void testFindUserByLogin() {
        // Arrange
        when(userPort.findUserByLogin(anyString())).thenReturn(Optional.of(mockUser));

        // Act
        Optional<User> userOptional = userUserCase.findUserByLogin("maria");

        // Assert
        assertTrue(userOptional.isPresent());
        assertEquals(mockUser.getId(), userOptional.get().getId());
        assertEquals(mockUser.getLogin(), userOptional.get().getLogin());
        verify(userPort, times(1)).findUserByLogin(anyString());
    }

    @Test
    void testFindAllUsers() {
        // Arrange
        User anotherUser = new User();
        anotherUser.setId(2L);
        anotherUser.setLogin("jose.th");
        anotherUser.setFirstName("José");
        anotherUser.setLastName("Thanos");

        when(userPort.findAllUsers()).thenReturn(List.of(mockUser, anotherUser));

        // Act
        List<User> users = userUserCase.findAllUsers();

        // Assert
        assertNotNull(users);
        assertEquals(2, users.size());
        verify(userPort, times(1)).findAllUsers();
    }
}
