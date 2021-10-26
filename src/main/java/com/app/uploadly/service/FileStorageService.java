package com.app.uploadly.service;

import com.app.uploadly.exceptions.FileIsEmptyException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
public interface FileStorageService {

    void uploadImageToCloudStorage(String bucketName, String objectName, String filePath);

    MultipartFile uploadFile(MultipartFile file) throws FileIsEmptyException;

}
