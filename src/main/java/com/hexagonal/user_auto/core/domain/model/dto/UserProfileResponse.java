package com.hexagonal.user_auto.core.domain.model.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.hexagonal.user_auto.adapters.repository.model.Car;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"firstName", "lastName", "email", "birthday", "login", "phone", "cars", "createdAt", "lastLogin"})
public class UserProfileResponse {

    private String firstName;
    private String lastName;
    private String email;
    private String birthday;
    private String login;
    private String phone;
    private List<Car> cars;
    private LocalDate createdAt;
    private LocalDateTime lastLogin;
}