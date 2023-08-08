package com.example.demo.dtos;

import com.example.demo.models.User;
import lombok.*;
import org.springframework.beans.BeanUtils;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private String firstName;
    private String lastName;
    private String phoneNo;
    private String email;
    private String username;

    public static UserDto toDto(User userModel) {
        UserDto dto = new UserDto();
        BeanUtils.copyProperties(userModel, dto);
        return dto;
    }
}
