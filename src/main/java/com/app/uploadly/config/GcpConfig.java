package com.app.uploadly.config;

import com.google.api.client.auth.oauth2.Credential;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

@Configuration
public class GcpConfig {

    @Bean
    public static Storage objectStorage() throws IOException {

        GoogleCredentials gcpCredentials = GoogleCredentials.fromStream(new FileInputStream(System.getenv("GOOGLE_APPLICATION_CREDENTIALS")));

        Storage storage = StorageOptions.newBuilder().setCredentials(gcpCredentials)
                .setProjectId(System.getenv("GCP_PROJECTID")).build().getService();

        return storage;
    }
}
