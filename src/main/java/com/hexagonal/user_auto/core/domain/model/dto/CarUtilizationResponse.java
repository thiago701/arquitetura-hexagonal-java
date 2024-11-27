package com.hexagonal.user_auto.core.domain.model.dto;

import lombok.*;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class CarUtilizationResponse {

    private String userFirstName;
    private String carModel;
    private int utilizationCount;

}
