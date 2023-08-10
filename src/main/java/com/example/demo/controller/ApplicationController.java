package com.example.demo.controller;


import com.example.demo.dtos.PageDto;
import com.example.demo.exceptions.CommonsModuleException;
import com.example.demo.models.IUserDetails;
import com.example.demo.service.ApplicationService;
import com.example.demo.utils.IAppendableReferenceUtils;
import com.example.demo.utils.PageRequestUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import static com.example.demo.enums.Authorities.RECRUITER_PREAUTHORIZE;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/application")
@CrossOrigin(origins = "*")
public class ApplicationController {

    private final ApplicationService applicationService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void apply(Authentication authentication, @RequestParam String jobId) throws CommonsModuleException {
        long userId = IUserDetails.getId(authentication);
        long jobIdLong = IAppendableReferenceUtils.getIdFrom(jobId);
        applicationService.apply(userId, jobIdLong);

    }

    @GetMapping
    public PageDto getApplications(Authentication authentication, @RequestParam int page,
                                   @RequestParam int size) {
        long userId = IUserDetails.getId(authentication);
        return applicationService.getApplications(userId, PageRequestUtils.normalize(page, size));
    }

    @GetMapping("applicants")
    @PreAuthorize(RECRUITER_PREAUTHORIZE)
    public PageDto getApplicants(@RequestParam String jobId, @RequestParam int page,
                                 @RequestParam int size) {
        long jobIdLong = IAppendableReferenceUtils.getIdFrom(jobId);
        return applicationService.getApplicants(jobIdLong, PageRequestUtils.normalize(page, size));
    }
}
