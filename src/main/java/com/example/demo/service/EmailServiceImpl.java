package com.example.demo.service;

import com.example.demo.models.User;
import com.example.demo.utils.EmailUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    private final EmailUtils emailUtils;


    @Override
    public void sendWelcomeEmail(String userName, String confirmationUrl, String userEmail) throws MessagingException {
     emailUtils.sendWelcomeEmail(userName, confirmationUrl,userEmail);
    }

    @Override
    public void sendMessageEmail(User intern, User recruiter, String message) throws MessagingException {
     emailUtils.sendMessageEmail(intern,recruiter,message);
    }
}
