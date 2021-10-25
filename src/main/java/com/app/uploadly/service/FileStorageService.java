package com.app.uploadly.service;

import org.springframework.stereotype.Service;


@Service
public interface FileStorageService {
    void uploadImage(String bucketName, String objectName, String filePath);

//    String generateShareableLink();
}
