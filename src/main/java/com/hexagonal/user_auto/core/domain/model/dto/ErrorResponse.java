package com.hexagonal.user_auto.core.domain.model.dto;

import lombok.*;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ErrorResponse {

    private String message;
    private Integer errorCode;

}
