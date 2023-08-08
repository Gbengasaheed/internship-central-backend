package com.example.demo.service;

import com.example.demo.dtos.SendMessageRequest;
import com.example.demo.exceptions.CommonsModuleException;

import javax.mail.MessagingException;

public interface MessagingService {
    void sendMessageToRecruiter(String userId, SendMessageRequest sendMessageRequest) throws CommonsModuleException, MessagingException;
}
