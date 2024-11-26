package com.hexagonal.user_auto.core.usercase;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class FileStorageUserCase {

    private static final String UPLOAD_DIRECTORY = "D:/uploads/";

    public String storeFile(MultipartFile file, String subDirectory) throws IOException {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("Arquivo não pode ser vazio");
        }

        String uploadDir = UPLOAD_DIRECTORY + subDirectory;
        File directory = new File(uploadDir);
        if (!directory.exists()) {
            directory.mkdirs(); // Cria o diretório, se não existir
        }

        File destinationFile = new File(uploadDir + "/" + file.getOriginalFilename());
        file.transferTo(destinationFile);

        return destinationFile.getAbsolutePath();
    }
}