package com.example.demo.dtos;

import com.example.demo.annotation.DoesNotExist;
import com.example.demo.annotation.ValidEmailAddress;
import com.example.demo.annotation.ValidPassword;
import com.example.demo.annotation.ValidPhoneNumber;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import static com.example.demo.utils.Constants.EntityColumns.EMAIL;
import static com.example.demo.utils.Constants.EntityColumns.PHONE_NO;
import static com.example.demo.utils.Constants.EntityNames.USERS;

@Getter
@Setter
public class CreateUserDto {
    @Size(max = 20, min = 2, message = "{firstname.accepted_length}")
    @NotBlank(message = "{firstname.not_blank}")
    private String firstName;

    @Size(max = 20, min = 2, message = "{lastname.accepted_length}")
    @NotBlank(message = "{lastname.not_blank}")
    private String lastName;

    @ValidPhoneNumber
    @NotBlank(message = "{phone_number.not_blank}")
    @DoesNotExist(table = USERS, columnName = PHONE_NO, message = "{user.phone_num_exists}")
    private String phoneNo;

    @ValidEmailAddress
    @NotBlank(message = "{email.not_blank}")
    @DoesNotExist(table = USERS, columnName = EMAIL, message = "{user.email_exists}")
    private String email;

    @NotBlank(message = "{password.not.empty}")
    @ValidPassword(message = "{password.not.strong}")
    private String password;
}