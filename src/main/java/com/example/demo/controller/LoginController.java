package com.example.demo.controller;

import com.example.demo.dtos.ILoginDto;
import com.example.demo.security.TokenProvider;

import javax.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.example.demo.utils.Constants.X_ACCESS_TOKEN;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class LoginController {
    private final TokenProvider tokenProvider;
    private final AuthenticationManager authenticationManager;

    @PostMapping("v6/authorize")
    public void authorize(@RequestBody @Valid ILoginDto loginRequest, HttpServletResponse httpServletResponse) {
        String password = loginRequest.getPassword();
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), password));
        httpServletResponse.addHeader(X_ACCESS_TOKEN, tokenProvider.generateJWTToken(authenticate));
        httpServletResponse.setHeader("access-control-expose-headers", X_ACCESS_TOKEN);
        httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());

        //  TODO: publish successful login event
    }
}
