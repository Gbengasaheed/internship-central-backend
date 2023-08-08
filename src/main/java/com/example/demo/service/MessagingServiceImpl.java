package com.example.demo.service;

import com.example.demo.dtos.SendMessageRequest;
import com.example.demo.exceptions.CommonsModuleException;
import com.example.demo.models.User;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;

@RequiredArgsConstructor
@Service
public class MessagingServiceImpl implements MessagingService{
    private final EmailService emailService;
    private final UserService userService;
    @Override
    public void sendMessageToRecruiter(String userId, SendMessageRequest sendMessageRequest) throws CommonsModuleException, MessagingException {
     User intern = userService.getUserById(Long.parseLong(userId));
     User recruiter = userService.getUserById(Long.parseLong(sendMessageRequest.getRecruiterId()));
     emailService.sendMessageEmail(intern,recruiter,sendMessageRequest.getMessage());
    }
}
