package com.app.uploadly.bucket;


public enum BucketIdentity {

    SINGLE_IMAGE("uploadly-bucket");

    private final String bucketImage;

    BucketIdentity(String bucketImage) {
        this.bucketImage = bucketImage;
    }

    public String getBucketImage(){
        return bucketImage;
    }
}
