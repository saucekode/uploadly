package com.app.uploadly.uploadlyImage;

import lombok.Data;

import java.util.UUID;

@Data
public class Image {
    private String imageLink; // gcp link

    public Image(String imageLink) {
        this.imageLink = imageLink;
    }
}
