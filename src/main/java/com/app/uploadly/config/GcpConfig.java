package com.app.uploadly.config;

import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GcpConfig {

    @Bean
    public Storage objectStorage() {

        Storage storage = StorageOptions.getDefaultInstance().getService();

        return storage;
    }
}
