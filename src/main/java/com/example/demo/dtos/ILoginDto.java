package com.example.demo.dtos;

import com.example.demo.annotation.ValidEmailAddress;
import com.example.demo.annotation.ValidPassword;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ILoginDto {
    @ValidEmailAddress
    private String email;

    @ValidPassword
    private String password;
}
