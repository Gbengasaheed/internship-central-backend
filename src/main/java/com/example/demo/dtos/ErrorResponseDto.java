package com.example.demo.dtos;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Getter
@Setter
public class ErrorResponseDto {
    private String status;
    private HashSet<String> errors;

    public ErrorResponseDto() {
        this.errors = new HashSet<>();
        this.setStatus("ERROR");
    }

    public static ResponseEntity<Object> build(HttpStatus status, String... errors) {
        return build(status, Arrays.asList(errors));
    }

    public static ResponseEntity<Object> build(HttpStatus status, List<String> errors) {
        ErrorResponseDto errorResponseDto = new ErrorResponseDto();
        errorResponseDto.getErrors().addAll(errors);
        return ResponseEntity.status(status).body(errorResponseDto);
    }

    public void addError(String message) {
        if (this.errors == null)
            this.errors = new HashSet<>();
        this.errors.add(message);
    }
}
