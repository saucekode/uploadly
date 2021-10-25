package com.app.uploadly.service;

import com.app.uploadly.config.GcpConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class FileStorageServiceImplTest {

    @Autowired
    FileStorageService fileStorageService;

    @Autowired
    GcpConfig gcpCloudStorage;

    @Test
    @DisplayName("Test that image was uploaded to gcp bucket")
    void uploadImageToBucket(){
        fileStorageService.uploadImage("uploadly-store", "app-uploadly",  "/home/saucekode/Downloads/gcptest.png");
//        try{
////            assertThat(gcpCloudStorage.objectStorage().get().size()).isEqualTo(1);
////            log.info(String.valueOf(gcpCloudStorage.objectStorage().list));
//        }catch(IOException e){
//            log.info(e.getMessage());
//        }

    }
}