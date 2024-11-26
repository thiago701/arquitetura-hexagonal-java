package com.hexagonal.user_auto.core.usercase;

import com.hexagonal.user_auto.adapters.repository.model.Car;
import com.hexagonal.user_auto.core.port.in.CarPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class CarUserCaseTest {

    @Mock
    private CarPort carPort;

    @InjectMocks
    private CarUserCase carUserCase;

    private Car mockCar;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockCar = new Car();
        mockCar.setId(1L);
        mockCar.setModel("Civic");
        mockCar.setLicensePlate("XYZ-1234");
        mockCar.setYearManufacture(2018);
        mockCar.setColor("Preto");
        mockCar.setCreatedAt(LocalDate.now());
    }

    @Test
    void testSaveCar() {
        // Arrange
        when(carPort.saveCar(any(Car.class))).thenReturn(mockCar);

        // Act
        Car savedCar = carUserCase.saveCar(mockCar);

        // Assert
        assertNotNull(savedCar);
        assertEquals(mockCar.getId(), savedCar.getId());
        assertEquals(mockCar.getModel(), savedCar.getModel());
        verify(carPort, times(1)).saveCar(any(Car.class));
    }

    @Test
    void testUpdateCar() {
        // Arrange
        when(carPort.updateCar(any(Car.class))).thenReturn(mockCar);

        // Act
        Car updatedCar = carUserCase.updateCar(mockCar);

        // Assert
        assertNotNull(updatedCar);
        assertEquals(mockCar.getId(), updatedCar.getId());
        assertEquals(mockCar.getModel(), updatedCar.getModel());
        verify(carPort, times(1)).updateCar(any(Car.class));
    }

    @Test
    void testDeleteCar() {
        // Act
        carUserCase.deleteCar(1L, "joana");

        // Assert
        verify(carPort, times(1)).deleteCar(anyLong(), anyString());
    }

    @Test
    void testFindCarById() {
        // Arrange
        when(carPort.findCarById(anyLong())).thenReturn(mockCar);

        // Act
        Car car = carUserCase.findCarById(1L);

        // Assert
        assertNotNull(car);
        assertEquals(mockCar.getId(), car.getId());
        assertEquals(mockCar.getModel(), car.getModel());
        verify(carPort, times(1)).findCarById(anyLong());
    }

    @Test
    void testFindAllCars() {
        // Arrange
        Car anotherCar = new Car();
        anotherCar.setId(2L);
        anotherCar.setModel("Corolla");
        anotherCar.setLicensePlate("ABC-5678");
        anotherCar.setYearManufacture(2020);
        anotherCar.setColor("Branco");
        anotherCar.setCreatedAt(LocalDate.now());

        when(carPort.findAllCars()).thenReturn(List.of(mockCar, anotherCar));

        // Act
        List<Car> cars = carUserCase.findAllCars();

        // Assert
        assertNotNull(cars);
        assertEquals(2, cars.size());
        verify(carPort, times(1)).findAllCars();
    }

    @Test
    void testFindCarsByUserLogin() {
        // Arrange
        when(carPort.findCarsByUserLogin(anyString())).thenReturn(List.of(mockCar));

        // Act
        List<Car> cars = carUserCase.findCarsByUserLogin("pedro987");

        // Assert
        assertNotNull(cars);
        assertEquals(1, cars.size());
        assertEquals(mockCar.getModel(), cars.get(0).getModel());
        verify(carPort, times(1)).findCarsByUserLogin(anyString());
    }

    @Test
    void testFindCarByIdAndUserLogin() {
        // Arrange
        when(carPort.findCarByIdAndUserLogin(anyLong(), anyString())).thenReturn(mockCar);

        // Act
        Car car = carUserCase.findCarByIdAndUserLogin(1L, "thiago-gg");

        // Assert
        assertNotNull(car);
        assertEquals(mockCar.getId(), car.getId());
        assertEquals(mockCar.getModel(), car.getModel());
        verify(carPort, times(1)).findCarByIdAndUserLogin(anyLong(), anyString());
    }
}
