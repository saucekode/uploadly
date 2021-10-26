package com.app.uploadly.uploadlyImage;

import com.app.uploadly.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
        storageService.uploadFile(file);
        return ResponseEntity.ok(file.getOriginalFilename() + " was uploaded successfully!");
    }

    @GetMapping("viewImage")
    public ResponseEntity<?> viewImage(){
        return null;
    }

}
