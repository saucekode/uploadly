package com.app.uploadly.service;

import com.app.uploadly.config.GcpConfig;
import com.app.uploadly.exceptions.FileIsEmptyException;
import com.app.uploadly.exceptions.UploadFailureException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class FileStorageServiceImplTest {

    @Autowired
    FileStorageService fileStorageService;

    @Test
    @DisplayName("Test that image can be uploaded")
    void uploadMultipartImage() throws IOException, FileIsEmptyException, UploadFailureException {
//        fileStorageService.uploadedFile();
        Path filePath = Paths.get("/home/saucekode/Pictures/definitions.png");
        MultipartFile multipartFile = new MockMultipartFile("definitions", "definitions.png", "img/png", Files.readAllBytes(filePath));

        log.info("Multipart --> {}", multipartFile);

        assertThat(multipartFile).isNotNull();

        fileStorageService.uploadFile(multipartFile);

    }
}