package com.hexagonal.user_auto.adapters.repository.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "tb_cars")
@Data
@ToString(exclude = "user")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "user")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long id;
    @DecimalMin(value = "1900", message = "Year is invalid (1900-2099)")
    @DecimalMax(value = "2099", message = "Year is invalid (1900-2099)")
    private Integer yearManufacture;
    @NotBlank(message = "Missing licensePlate")
    private String licensePlate;
    @NotBlank(message = "Missing model")
    private String model;
    @NotBlank(message = "Missing color")
    private String color;
    private LocalDate createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    private User user;

}
