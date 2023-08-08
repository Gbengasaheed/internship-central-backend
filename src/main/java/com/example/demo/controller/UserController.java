package com.example.demo.controller;

import com.example.demo.dtos.ResponseDto;
import com.example.demo.dtos.UpdateUserDto;
import com.example.demo.dtos.UserDto;
import com.example.demo.exceptions.CommonsModuleException;
import com.example.demo.models.IUserDetails;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.lang.reflect.InvocationTargetException;

import static org.springframework.http.HttpStatus.OK;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v6")
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userServiceImpl;

    @GetMapping("user")
    public ResponseDto<?> getUser(Authentication authentication) throws CommonsModuleException {
        long userId = IUserDetails.getId(authentication);
        UserDto userDto = userServiceImpl.findUserById(userId);
        return ResponseDto.wrapSuccessResult(userDto, "request.successful");
    }

    @PutMapping("/update")
    @ResponseStatus(OK)
    public ResponseDto<?> updateUser(Authentication authentication, @RequestBody @Valid UpdateUserDto data) throws
            InvocationTargetException, IllegalAccessException, CommonsModuleException {
        long userId = IUserDetails.getId(authentication);
        UserDto user = userServiceImpl.updateUser(userId, data);
        return ResponseDto.wrapSuccessResult(user, "request.successful");
    }


    @GetMapping("userDetails")
    public ResponseDto<?> getUserDetails(Authentication authentication) throws CommonsModuleException {
        long userId = IUserDetails.getId(authentication);
        return ResponseDto.wrapSuccessResult(userServiceImpl.getUser(userId), "request.successful");
    }
}
