package com.management.project.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CommonResponse<T> {
    @Builder.Default()
    private String status="200";  // Default status to 200;
    private String message="Success";  // Default status to 200;
    private T data;
    private String error;


    public CommonResponse(String status, String message, String error) {
        this.status = status;
        this.message = message;
        this.error = error;
    }

    public CommonResponse(T data) {
        this.status = "200";
        this.message = "Success";
        this.data = data;
        this.error = null;

    }
}
