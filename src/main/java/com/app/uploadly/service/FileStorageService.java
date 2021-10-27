package com.app.uploadly.service;

import com.app.uploadly.exceptions.FileIsEmptyException;
import com.app.uploadly.exceptions.UploadFailureException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@Service
public interface FileStorageService {

    String uploadImageToCloudStorage(String bucketName, MultipartFile fileToTransfer) throws UploadFailureException, FileIsEmptyException;

}
