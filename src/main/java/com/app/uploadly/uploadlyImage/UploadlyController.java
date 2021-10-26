package com.app.uploadly.uploadlyImage;

import com.app.uploadly.exceptions.FileIsEmptyException;
import com.app.uploadly.exceptions.UploadFailureException;
import com.app.uploadly.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/")
@CrossOrigin(origins = "http://localhost:3000")
public class UploadlyController {

    @Autowired
    private FileStorageService storageService;

    @PostMapping(
            path="upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<?> uploadImage(@RequestParam("imageFile") MultipartFile file){
        try{
            storageService.uploadFile(file);
        }catch(FileIsEmptyException ex){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No file was uploaded. Upload file!" ,ex);
        }catch(UploadFailureException ex){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid file type", ex);
        }
        return ResponseEntity.ok(file.getOriginalFilename() + " was uploaded successfully!");
    }

    @GetMapping("viewImage")
    public ResponseEntity<?> viewImage(){
        return null;
    }

}
