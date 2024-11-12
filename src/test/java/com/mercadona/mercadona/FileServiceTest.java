package com.mercadona.mercadona.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FileServiceTest {

    private static final String UPLOAD_DIR = "/tmp/images";

    @InjectMocks
    private FileService fileService;

    @BeforeEach
    void setUp() {
        fileService = new FileService();
    }

    @Test
    void testSaveImage_ThrowsException() {
        MockMultipartFile imageFile = new MockMultipartFile("image", "testImage.jpg", "image/jpeg", "test image content".getBytes());

        // Mock Files to throw an IOException
        try (MockedStatic<Paths> pathsMock = Mockito.mockStatic(Paths.class);
             MockedStatic<Files> filesMock = Mockito.mockStatic(Files.class)) {

            Path uploadPath = mock(Path.class);
            pathsMock.when(() -> Paths.get(UPLOAD_DIR)).thenReturn(uploadPath);
            filesMock.when(() -> Files.exists(uploadPath)).thenReturn(false);
            filesMock.when(() -> Files.createDirectories(uploadPath)).thenThrow(IOException.class);

            RuntimeException exception = assertThrows(RuntimeException.class, () -> fileService.saveImage(imageFile));

            assertEquals("Could not store the image", exception.getMessage());
        }
    }
}
