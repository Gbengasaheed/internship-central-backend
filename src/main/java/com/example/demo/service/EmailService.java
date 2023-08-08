package com.example.demo.service;

import com.example.demo.models.User;

import javax.mail.MessagingException;

public interface EmailService {
    void sendWelcomeEmail(String userName, String confirmationUrl, String userEmail) throws MessagingException;
    void sendMessageEmail(User intern, User recruiter, String message) throws MessagingException;
}
