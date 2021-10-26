package com.app.uploadly.service;

import com.app.uploadly.config.GcpConfig;
import com.app.uploadly.exceptions.FileIsEmptyException;
import com.app.uploadly.exceptions.UploadFailureException;
import com.app.uploadly.exceptions.UploadlyExceptions;
import com.google.cloud.storage.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

import static org.apache.http.entity.ContentType.*;

@Slf4j
@Service
public class FileStorageServiceImpl implements FileStorageService {
    @Autowired
    private GcpConfig gcpCloudStorage;

    @Override
    public void uploadImageToCloudStorage(String bucketName, String objectName, String filePath) {
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

    @Override
    public MultipartFile uploadFile(MultipartFile file) throws FileIsEmptyException, UploadFailureException {
        // check if file is empty
        if(file.isEmpty()){
            throw new FileIsEmptyException("File is empty");
        }
        // restrict file type
        if(!Arrays.asList(IMAGE_JPEG, IMAGE_PNG, IMAGE_SVG).toString().contains(file.getContentType())){
//            log.info(IMAGE_PNG.toString());
            throw new UploadFailureException("File must be an image!");
        }
        return file;
    }


}
