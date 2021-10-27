package com.app.uploadly.uploadlyImage;

import com.app.uploadly.exceptions.FileIsEmptyException;
import com.app.uploadly.exceptions.UploadFailureException;
import com.app.uploadly.payload.ApiResponse;
import com.app.uploadly.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

@RestController
@RequestMapping("/api/")
@CrossOrigin(origins = {"http://localhost:3000", "https://youthful-boyd-976ab9.netlify.app/"} )
public class UploadlyController {

    @Autowired
    private FileStorageService storageService;

    @PostMapping(
            path="upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<?> uploadImageToCloudStorage(@RequestParam("imageFile") MultipartFile file){
        String gcpObjectLink = null;
        try{
            gcpObjectLink = storageService.uploadImageToCloudStorage(System.getenv("GCP_BUCKET"), file);
        }catch(FileIsEmptyException ex){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No file was uploaded. Upload file!", ex);
        }catch(UploadFailureException ex){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "File must be an image!", ex);
        }
        return ResponseEntity.ok(new ApiResponse(true, gcpObjectLink));
    }

}
