package com.hexagonal.user_auto.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@CrossOrigin(origins = {"http://localhost:4200", "https://user-auto-front-dac15affdc70.herokuapp.com"})
@RestController
@Tag(name = "Home Controller", description = "Home padrão que redireciona '/' para o Swagger")
public class HomeController {

    @GetMapping("/")
    @Operation(summary = "Redireciona para o Swagger", description = "Home > Swagger")
    public ResponseEntity<Void> redirectToSwaggerUi() {
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/swagger-ui/index.html"));
        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }
}
