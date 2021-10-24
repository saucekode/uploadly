package com.app.uploadly.service;

import org.springframework.stereotype.Service;

import javax.swing.*;
import java.io.InputStream;
import java.util.Map;
import java.util.Optional;

@Service
public interface FileStorageService {
    void uploadImage(String projectId, String bucketName, String objectName, String filePath);


}
