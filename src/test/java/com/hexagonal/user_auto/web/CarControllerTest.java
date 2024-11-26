package com.hexagonal.user_auto.web;

import com.hexagonal.user_auto.adapters.repository.model.Car;
import com.hexagonal.user_auto.adapters.repository.model.User;
import com.hexagonal.user_auto.core.usercase.CarUserCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class CarControllerTest {

    @Mock
    private CarUserCase carUserCase;

    @Mock
    private BindingResult bindingResult;

    @Mock
    private Authentication authentication;

    @Mock
    private SecurityContext securityContext;

    @InjectMocks
    private CarController carController;

    private User mockUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockUser = new User();
        mockUser.setId(1L);
        mockUser.setLogin("nilo.silva");
        mockUser.setFirstName("Nilo");
        mockUser.setLastName("Silva");
        mockUser.setLastLogin(LocalDateTime.now());

        // Config SecurityContext
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(mockUser);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void testCreateCarSuccess() {
        // Mock do BindingResult sem erros
        when(bindingResult.hasErrors()).thenReturn(false);

        Car car = new Car();
        car.setId(1L);
        car.setModel("Civic");
        car.setLicensePlate("ABC-1234");

        when(carUserCase.saveCar(any(Car.class))).thenReturn(car);

        ResponseEntity<?> response = carController.createCar(car, bindingResult);

        assertEquals(201, response.getStatusCodeValue());
        assertEquals(car, response.getBody());
        assertEquals(URI.create("/cars/1"), response.getHeaders().getLocation());
        verify(carUserCase, times(1)).saveCar(any(Car.class));
    }

    @Test
    void testCreateCarValidationFailure() {
        // Mock do BindingResult com erro
        when(bindingResult.hasErrors()).thenReturn(true);
        when(bindingResult.getFieldErrors()).thenReturn(List.of());

        Car car = new Car();
        ResponseEntity<?> response = carController.createCar(car, bindingResult);

        assertEquals(400, response.getStatusCodeValue());
        verify(carUserCase, never()).saveCar(any(Car.class));
    }

    @Test
    void testUpdateCarSuccess() {
        Car car = new Car();
        car.setModel("Civic");
        car.setLicensePlate("ABC-1234");

        when(carUserCase.updateCar(any(Car.class))).thenReturn(car);

        ResponseEntity<Car> response = carController.updateCar(1L, car);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(car, response.getBody());
        verify(carUserCase, times(1)).updateCar(any(Car.class));
    }

    @Test
    void testDeleteCarSuccess() {
        doNothing().when(carUserCase).deleteCar(anyLong(), anyString());

        ResponseEntity<Void> response = carController.deleteCar(1L);

        assertEquals(204, response.getStatusCodeValue());
        verify(carUserCase, times(1)).deleteCar(anyLong(), anyString());
    }

    @Test
    void testGetCarByIdSuccess() {
        Car car = new Car();
        car.setId(1L);
        car.setModel("Civic");
        car.setLicensePlate("ABC-1234");

        when(carUserCase.findCarByIdAndUserLogin(anyLong(), anyString())).thenReturn(car);

        ResponseEntity<Car> response = carController.getCarById(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(car, response.getBody());
        verify(carUserCase, times(1)).findCarByIdAndUserLogin(anyLong(), anyString());
    }

    @Test
    void testGetUserCarsSuccess() {
        Car car1 = new Car();
        car1.setId(1L);
        car1.setModel("Civic");
        car1.setLicensePlate("ABC-1234");

        Car car2 = new Car();
        car2.setId(2L);
        car2.setModel("Corolla");
        car2.setLicensePlate("DEF-5678");

        when(carUserCase.findCarsByUserLogin(anyString())).thenReturn(List.of(car1, car2));

        ResponseEntity<List<Car>> response = carController.getUserCars();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(2, response.getBody().size());
        verify(carUserCase, times(1)).findCarsByUserLogin(anyString());
    }
}
