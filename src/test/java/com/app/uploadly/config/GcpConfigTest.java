package com.app.uploadly.config;

import com.google.cloud.storage.ServiceAccount;
import com.google.cloud.storage.Storage;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class GcpConfigTest {

    @Test
    @DisplayName("Test content of gcp service account")
    void testGcpCredentials(){
        GcpConfig gcpConfig = new GcpConfig();
        ServiceAccount serviceAccountName = null;
        try{
            serviceAccountName = gcpConfig.storage().getServiceAccount("uploadly-329921");
            log.info(serviceAccountName.getEmail());
        }catch(IOException e){
            log.info("Ioexception here is ----> {}", e.getMessage());
        }
        assertThat(serviceAccountName).isNotNull();
    }
}