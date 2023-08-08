package com.example.demo.controller;

import com.example.demo.dtos.*;
import com.example.demo.exceptions.CommonsModuleException;
import com.example.demo.models.IUserDetails;
import com.example.demo.service.ExperienceService;
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
@RequestMapping("/api/experience")
@CrossOrigin(origins = "*")
public class ExperienceController {

    private final ExperienceService experienceService;

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public void addExperience(Authentication authentication, @RequestBody @Valid AddExperienceRequest addExperienceRequest) {
        long userId = IUserDetails.getId(authentication);
        experienceService.addExperience(userId, addExperienceRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseDto<?> getUserExperiences(Authentication authentication) {
        long userId = IUserDetails.getId(authentication);
        List<ExperienceDto> experiences = experienceService.getUserExperiences(userId);
        return ResponseDto.wrapSuccessResult(experiences, "request.successful");
    }

    @PatchMapping("update")
    @ResponseStatus(HttpStatus.OK)
    public void update(@RequestParam String experienceId, @RequestBody UpdateExperienceRequest updateExperienceRequest) throws CommonsModuleException, InvocationTargetException, IllegalAccessException {
        long id = IAppendableReferenceUtils.getIdFrom(experienceId);
        experienceService.update(id, updateExperienceRequest);
    }
}
