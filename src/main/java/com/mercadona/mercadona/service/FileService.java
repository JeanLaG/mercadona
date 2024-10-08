package com.mercadona.mercadona.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileService {

    private final String uploadDir = "/tmp/images";

    public String saveImage(MultipartFile imageFile) {
        try {
            // Créer le répertoire s'il n'existe pas
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String filename = UUID.randomUUID().toString() + "_" + imageFile.getOriginalFilename();
            Path filePath = Paths.get(uploadDir, filename);
            Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            return "/images/" + filename;
        } catch (IOException e) {
            throw new RuntimeException("Could not store the image", e);
        }
    }
}

