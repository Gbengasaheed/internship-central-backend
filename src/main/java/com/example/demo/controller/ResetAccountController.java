package com.example.demo.controller;

import com.example.demo.dtos.ResetPasswordDTO;
import com.example.demo.models.IUserDetails;
import com.example.demo.security.TokenProvider;
import com.example.demo.service.UserService;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("api/v6/")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ResetAccountController {

    private final UserService userServiceImpl;

    private final TokenProvider tokenProvider;

    @PostMapping("user/password")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void resetPassword(Authentication authentication, @RequestBody @Valid ResetPasswordDTO resetPasswordDTO,
                              HttpServletResponse httpServletResponse) throws JwtException {
        IUserDetails iUserDetails = (IUserDetails) authentication.getPrincipal();
        String hashedPassword = userServiceImpl.updateUserPassword(iUserDetails, resetPasswordDTO);
        iUserDetails.setPassword(hashedPassword);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = tokenProvider.generateJWTToken(authentication);
        httpServletResponse.addHeader("x-access-token", token);
        // TODO: Send notification event for password reset
    }
}
