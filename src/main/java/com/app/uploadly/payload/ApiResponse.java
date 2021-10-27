package com.app.uploadly.payload;

import lombok.Data;

@Data
public class ApiResponse {
    private boolean isSuccessful;
    private String result; // gcp link

    public ApiResponse(boolean isSuccessful, String result) {
        this.isSuccessful = isSuccessful;
        this.result = result;
    }
}
