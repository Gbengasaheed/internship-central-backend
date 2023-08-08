package com.example.demo.dtos;

import com.example.demo.annotation.ValidPassword;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ResetPasswordDTO {
    @NotBlank(message = "{password.not.empty}")
    @ValidPassword(message = "{password.not.strong}")
    private String password;
}
