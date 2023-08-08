package com.example.demo.service;

import com.example.demo.dtos.*;
import com.example.demo.enums.Authorities;
import com.example.demo.exceptions.CommonsModuleException;
import com.example.demo.models.IUserDetails;
import com.example.demo.models.User;
import lombok.NonNull;

import javax.mail.MessagingException;
import java.lang.reflect.InvocationTargetException;

public interface UserService {
    User getUserById(long userId) throws CommonsModuleException;

    User saveUser(User user);

    User registerNewUser(CreateUserDto signupDto, Authorities... authorities) throws MessagingException;

    UserDto findUserById(long userId) throws CommonsModuleException;

    void createUserDefaultRoles(String username, Authorities... authorities);

    String updateUserPassword(IUserDetails user, final ResetPasswordDTO resetPasswordDTO);

    UserDto updateUser(long userId, @NonNull UpdateUserDto dto) throws
            InvocationTargetException, IllegalAccessException, CommonsModuleException;

    void updateEmailVerificationStatus(long id, String email);

    void verifyUser(String username,String token) throws CommonsModuleException;
  
    SuperUserDto getUser(long userId) throws CommonsModuleException;

}
