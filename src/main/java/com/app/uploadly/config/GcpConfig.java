package com.app.uploadly.config;

import com.google.api.client.auth.oauth2.Credential;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Configuration
@Slf4j
public class GcpConfig {

    @Bean
    public Storage objectStorage() throws IOException {

        GoogleCredentials gcpCredentials = GoogleCredentials.fromStream(new FileInputStream("https://storage.cloud.google.com/gcpcred/uploadlycred.json"));

        Storage storage = StorageOptions.newBuilder().setCredentials(gcpCredentials)
                .setProjectId("app-uploadly").build().getService();

        return storage;
    }
}
