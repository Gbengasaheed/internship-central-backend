package com.example.demo.service;

import com.example.demo.dtos.*;
import com.example.demo.enums.Authorities;
import com.example.demo.exceptions.CommonsModuleException;
import com.example.demo.models.IAuthorities;
import com.example.demo.models.IUserDetails;
import com.example.demo.models.User;
import com.example.demo.repositories.AuthorityRepository;
import com.example.demo.repositories.UserRepository;
import com.example.demo.utils.BeanUtilHelper;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthorityRepository authorityRepository;
    private final TokenVerificationService tokenVerificationService;
    private final EmailService emailService;
    @Value(value = "${verify.url}")
    private String verifyUrl;

    private final ExperienceService experienceService;

    private final EducationService educationService;

    private String generateRandomUsername() {
        String username = RandomStringUtils.randomAlphanumeric(10);
        String nextInt = String.valueOf(RandomUtils.nextInt(99, 999));
        return username + nextInt;
    }

    public User getUserById(long userId) throws CommonsModuleException {
        return userRepository.findById(userId).orElseThrow(() -> new CommonsModuleException("user.does.not.exist", HttpStatus.NOT_FOUND));
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    private User createUser(CreateUserDto createUserDto) {
        User user = new User();
        String username = generateRandomUsername();
        BeanUtils.copyProperties(createUserDto, user);
        user.setUsername(username);
        final String hashedPassword = passwordEncoder.encode(createUserDto.getPassword());
        user.setPassword(hashedPassword);
        return saveUser(user);
    }

    public UserDto findUserById(long userId) throws CommonsModuleException {
        User user = getUserById(userId);
        return UserDto.toDto(user);
    }

    public User registerNewUser(CreateUserDto signupDto, Authorities... authorities) throws MessagingException {
        User user = createUser(signupDto);
        final String username = user.getEmail();

        createUserDefaultRoles(username, authorities);
        String token = tokenVerificationService.generateToken();
        verifyUrl = verifyUrl + user.getUsername() + "/";
        verifyUrl += token;
        emailService.sendWelcomeEmail(user.getFirstName(), verifyUrl, user.getEmail());
        return user;
    }

    public void createUserDefaultRoles(String username, Authorities... authorities) {
        Set<IAuthorities> authoritySet = new HashSet<>();
        for (Authorities authority : authorities) {
            IAuthorities userAuthority = new IAuthorities();
            userAuthority.setAuthority(authority.name());
            userAuthority.setEmail(username);
            authoritySet.add(userAuthority);
        }
        authorityRepository.saveAll(authoritySet);
    }


    public String updateUserPassword(IUserDetails user, final ResetPasswordDTO resetPasswordDTO) {
        String hashedPassword = passwordEncoder.encode(resetPasswordDTO.getPassword());
        userRepository.updateUserPassword(user.getId(), hashedPassword);
        return hashedPassword;
    }

    public UserDto updateUser(long userId, @NonNull UpdateUserDto dto) throws
            InvocationTargetException, IllegalAccessException, CommonsModuleException {
        User user = getUserById(userId);
        BeanUtilHelper.copyPropertiesIgnoreNull(dto, user);
        return UserDto.toDto(saveUser(user));
    }

    private User findByUsername(String username) throws CommonsModuleException {
        return userRepository.findByUsername(username).orElseThrow(() -> new CommonsModuleException("user.does.not.exist", HttpStatus.NOT_FOUND));
    }

    public void updateEmailVerificationStatus(long id, String email) {
        userRepository.updateEmailByUserId(id, email);
    }


    @Override
    public void verifyUser(String username, String token) throws CommonsModuleException {
        User user = findByUsername(username);
        if (tokenVerificationService.verifyToken(token)) {
            userRepository.updateEmailVerificationStatus(user.getId(), user.getEmail());
        } else {
            throw new CommonsModuleException("invalid token", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public SuperUserDto getUser(long userId) throws CommonsModuleException {
        SuperUserDto superUserDto = new SuperUserDto();
        UserDto userDto = findUserById(userId);
        BeanUtils.copyProperties(userDto, superUserDto);
        superUserDto.setEducationDtoList(educationService.getUserEducations(userId));
        superUserDto.setExperienceDtoList(experienceService.getUserExperiences(userId));
        return superUserDto;
    }
}
