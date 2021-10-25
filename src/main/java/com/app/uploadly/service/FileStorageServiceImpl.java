package com.app.uploadly.service;

import com.app.uploadly.config.GcpConfig;
import com.google.cloud.storage.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class FileStorageServiceImpl implements FileStorageService {
    @Autowired
    private GcpConfig gcpCloudStorage;

    @Override
    public void uploadImage(String bucketName, String objectName, String filePath) {
        try{
            Storage storage = gcpCloudStorage.objectStorage();
            BlobId blobId = BlobId.of(bucketName, objectName);
            BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
            storage.create(blobInfo, Files.readAllBytes(Paths.get(filePath)));

            Blob blob = storage.get(BlobId.of(bucketName, objectName));

            log.info("File uploaded successfully to bucket --> {}", bucketName);
            log.info(blob.getSelfLink());

        }catch(IOException ex){
            throw new IllegalStateException("Failed to upload image ", ex);
        }
    }


}
