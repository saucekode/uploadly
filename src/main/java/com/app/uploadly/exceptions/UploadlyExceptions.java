package com.app.uploadly.exceptions;

public class UploadlyExceptions extends Exception{
    public UploadlyExceptions() {
    }

    public UploadlyExceptions(String message) {
        super(message);
    }

    public UploadlyExceptions(String message, Throwable cause) {
        super(message, cause);
    }

    public UploadlyExceptions(Throwable cause) {
        super(cause);
    }
}
