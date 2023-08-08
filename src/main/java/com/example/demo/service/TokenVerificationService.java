package com.example.demo.service;

public interface TokenVerificationService {
    String generateToken();
    Boolean verifyToken(String token);
}
