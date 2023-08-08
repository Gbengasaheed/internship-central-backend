package com.example.demo.controller;

import com.example.demo.dtos.CompanyDto;
import com.example.demo.dtos.CreateRecruiterRequest;
import com.example.demo.dtos.CreateUserDto;
import com.example.demo.enums.Authorities;
import com.example.demo.exceptions.CommonsModuleException;
import com.example.demo.models.User;
import com.example.demo.service.CompanyService;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.Valid;

import static org.springframework.http.HttpStatus.OK;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/")
@CrossOrigin(origins = "*")
public class SignUpController {

    private final UserService userServiceImpl;

    private final CompanyService companyService;

    @PostMapping("v6/signUp")
    @ResponseStatus(HttpStatus.CREATED)
    public void createUser(@RequestBody @Valid CreateUserDto signupDto) throws MessagingException {
        userServiceImpl.registerNewUser(signupDto, Authorities.USER);
    }

    @PostMapping("v6/recruiter-signUp")
    @ResponseStatus(HttpStatus.CREATED)
    public void createRecruiter(@RequestBody @Valid CreateRecruiterRequest signupDto)throws MessagingException {
        User user = userServiceImpl.registerNewUser(signupDto, Authorities.RECRUITER);
        CompanyDto companyDto = signupDto.getCompany();
        companyService.addCompany(user.getId(), companyDto);
    }

    @RequestMapping("v6/verify/{username}/{token}")
    @ResponseStatus(OK)
    public void verify(@PathVariable("username") String username,@PathVariable("token") String token) throws CommonsModuleException {
        userServiceImpl.verifyUser(username,token);
    }}
