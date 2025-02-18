package com.hexagonal.user_auto.adapters.repository.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tb_cars_utilization")
@Data
@ToString(exclude = "user.cars")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class CarUtilization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_id", nullable = false)
    private Car car;

    private Integer utilizationCount;

    public CarUtilization(Car car, User user, int i) {
        this.car = car;
        this.user = user;
        this.utilizationCount = i;
    }

    public void incrementUsage() {
        this.utilizationCount++;
    }
}

