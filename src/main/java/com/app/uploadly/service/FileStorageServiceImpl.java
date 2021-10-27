package com.app.uploadly.service;

import com.app.uploadly.config.GcpConfig;
import com.app.uploadly.exceptions.FileIsEmptyException;
import com.app.uploadly.exceptions.UploadFailureException;
import com.google.cloud.storage.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import static org.apache.http.entity.ContentType.*;

@Slf4j
@Service
public class FileStorageServiceImpl implements FileStorageService {

    @Autowired
    private GcpConfig gcpCloudStorage;

    @Override
    public void uploadImageToCloudStorage(String bucketName, MultipartFile fileToTransfer)
            throws UploadFailureException, FileIsEmptyException {

        try{

            Storage storage = gcpCloudStorage.objectStorage();

            BlobId blobId = BlobId.of(bucketName, fileToTransfer.getName() + "." + fileToTransfer.getContentType());

            BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();

            storage.create(blobInfo, Files.readAllBytes(Paths.get(String.valueOf(convertMultipartToFile(fileToTransfer)))));

            log.info("File uploaded successfully to bucket --> {}", bucketName);

        }catch(IOException ex){

//            log.info(String.valueOf(newFile));
            
            throw new IllegalStateException("Failed to upload image ", ex);

        }
    }

    private MultipartFile uploadFile(MultipartFile file) throws FileIsEmptyException, UploadFailureException {

        // check if file is empty
        if(file.isEmpty()){
            throw new FileIsEmptyException("File is empty");
        }

        // restrict file type
        if(!String.valueOf(Arrays.asList(IMAGE_JPEG, IMAGE_PNG, IMAGE_SVG)).contains(file.getContentType())){
            throw new UploadFailureException("File must be an image!");
        }

        if(file.getSize() > 3_000_000){
            throw new UploadFailureException("Maximum file size exceeded!!");
        }

        return file;

    }

    private File convertMultipartToFile(MultipartFile fileToConvert) throws UploadFailureException, FileIsEmptyException, IOException {

        MultipartFile file = uploadFile(fileToConvert);

        File convertedFile = new File(System.getProperty("java.io.tmpdir")+"/"+file.getOriginalFilename());

        file.transferTo(convertedFile);

//        log.info(convertedFile.toString());

        return convertedFile;
    }

}
