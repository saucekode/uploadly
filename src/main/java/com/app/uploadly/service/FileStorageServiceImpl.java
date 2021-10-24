package com.app.uploadly.service;

import com.app.uploadly.config.GcpConfig;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Optional;

@Slf4j
public class FileStorageServiceImpl implements FileStorageService {
    @Autowired
    private GcpConfig gcpCloudStorage;

    @Override
    public void uploadImage(String projectId, String bucketName, String objectName, String filePath) {
        try{
            BlobId blobId = BlobId.of(bucketName, objectName);
            BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
            gcpCloudStorage.objectStorage().create(blobInfo, Files.readAllBytes(Paths.get(filePath)));
        }catch(IOException ex){
            log.info("Catch file exceptions here ---> {}", ex.getMessage());
        }
    }


}
