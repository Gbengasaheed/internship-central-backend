package com.example.demo.controller;

import com.example.demo.dtos.CreateJobRequest;
import com.example.demo.dtos.SendMessageRequest;
import com.example.demo.exceptions.CommonsModuleException;
import com.example.demo.models.IUserDetails;
import com.example.demo.service.MessagingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/messaging")
@CrossOrigin(origins = "*")
public class MessagingController {
    private final MessagingService messagingService;
    @RequestMapping()
    @ResponseStatus(HttpStatus.OK)
    public void sendMessage(Authentication authentication, @RequestBody @Valid SendMessageRequest sendMessageRequest) throws CommonsModuleException, MessagingException {
        long userId = IUserDetails.getId(authentication);
        messagingService.sendMessageToRecruiter(String.valueOf(userId),sendMessageRequest);
    }
}
