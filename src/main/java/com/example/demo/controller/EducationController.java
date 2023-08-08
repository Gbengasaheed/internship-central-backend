package com.example.demo.controller;

import com.example.demo.dtos.*;
import com.example.demo.exceptions.CommonsModuleException;
import com.example.demo.models.IUserDetails;
import com.example.demo.service.EducationService;
import com.example.demo.utils.IAppendableReferenceUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/education")
@CrossOrigin(origins = "*")
public class EducationController {

    private final EducationService educationService;

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public void addEducation(Authentication authentication, @RequestBody @Valid AddEducationRequest addEducationRequest) {
        long userId = IUserDetails.getId(authentication);
       educationService.addEducation(userId, addEducationRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseDto<?> getUserEducations(Authentication authentication) throws CommonsModuleException {
        long userId = IUserDetails.getId(authentication);
        List<EducationDto> experiences = educationService.getUserEducations(userId);
        return ResponseDto.wrapSuccessResult(experiences, "request.successful");
    }

    @PatchMapping("update")
    @ResponseStatus(HttpStatus.OK)
    public void update(@RequestParam String experienceId, @RequestBody UpdateEducationRequest updateEducationRequest) throws CommonsModuleException, InvocationTargetException, IllegalAccessException {
        long id = IAppendableReferenceUtils.getIdFrom(experienceId);
        educationService.update(id, updateEducationRequest);
    }
}
