package com.hexagonal.user_auto.web;

import com.hexagonal.user_auto.core.usercase.FileStorageUserCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/uploads")
@Tag(name = "Upload Controller", description = "Operações relacionadas ao envio de fotos")
public class FileUploadController {

    @Autowired
    private FileStorageUserCase fileStorageUserCase;

    @PostMapping("/user/photo/{id}")
    @Operation(summary = "Upload user", description = "Envia uma foto de usuario")
    public ResponseEntity<String> uploadUserPhoto(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        try {
            String filePath = fileStorageUserCase.storeFile(file, "user/"+id); //Pasta user + ID do usuario
            return ResponseEntity.ok("Arquivo enviado com sucesso: " + filePath);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao salvar o arquivo: " + e.getMessage());
        }
    }

    @PostMapping("/car/photo/{id}")
    @Operation(summary = "Upload car", description = "Envia uma foto do carro")
    public ResponseEntity<String> uploadCarPhoto(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        try {
            String filePath = fileStorageUserCase.storeFile(file, "car/"+id); // Pasta car + ID do carro
            return ResponseEntity.ok("Arquivo enviado com sucesso: " + filePath);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao salvar o arquivo: " + e.getMessage());
        }
    }
}
