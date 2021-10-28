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
import java.util.UUID;

import static com.app.uploadly.config.GcpConfig.objectStorage;
import static org.apache.http.entity.ContentType.*;

@Slf4j
@Service
public class FileStorageServiceImpl implements FileStorageService {


    @Override
    public String uploadImageToCloudStorage(String bucketName, MultipartFile fileToTransfer)
            throws UploadFailureException, FileIsEmptyException {

        try{

            Storage storage = objectStorage();

            BlobId blobId = BlobId.of(bucketName, String.valueOf(UUID.randomUUID() + "." + fileToTransfer.getContentType().split("/")[0]));

            BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();


            return generateGcpLink(storage, blobInfo, fileToTransfer);

//            log.info("File uploaded successfully to bucket --> {}", bucketName);

        }catch(IOException ex){

//            log.info(String.valueOf(newFile));
            
            throw new IllegalStateException("Failed to upload image ", ex);

        }


    }

    private MultipartFile uploadFile(MultipartFile file) throws FileIsEmptyException, UploadFailureException {

        // check if file is empty
        fileIsNotEmpty(file);
        // validate file size
        fileSizeValidation(file);
        // restrict file type
        fileTypeValidation(file);

        return file;

    }

    private File convertMultipartToFile(MultipartFile fileToConvert) throws UploadFailureException, FileIsEmptyException, IOException {

        MultipartFile file = uploadFile(fileToConvert);

        File convertedFile = new File(System.getProperty("java.io.tmpdir")+"/"+file.getOriginalFilename());

        file.transferTo(convertedFile);

//        log.info(convertedFile.toString());

        return convertedFile;
    }

    private void fileTypeValidation(MultipartFile file) throws UploadFailureException {
        if(!String.valueOf(Arrays.asList(IMAGE_JPEG, IMAGE_PNG, IMAGE_SVG)).contains(file.getContentType())){
            throw new UploadFailureException("File must be an image!");
        }
    }

    private void fileSizeValidation(MultipartFile file) throws UploadFailureException {
        if(file.getSize() > 3_000_000){
            throw new UploadFailureException("Maximum file size exceeded!!");
        }
    }

    private void fileIsNotEmpty(MultipartFile file) throws FileIsEmptyException {
        if(file.isEmpty()){
            throw new FileIsEmptyException("File is empty");
        }
    }

    private String generateGcpLink(Storage storage, BlobInfo blobInfo, MultipartFile file) throws UploadFailureException, FileIsEmptyException, IOException {

        StringBuilder gcpLink = new StringBuilder("https://storage.googleapis.com/");
        gcpLink.append(System.getenv("GCP_BUCKET"));
        gcpLink.append("/");
        gcpLink.append(storage.create(blobInfo, Files.readAllBytes(Paths.get(String.valueOf(convertMultipartToFile(file))))).getName());

        return String.valueOf(gcpLink);

    }

}
