package com.example.demo.dtos;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SendMessageRequest {
    private String recruiterId;
    private String message;
}
