package com.example.demo.controller;

import com.example.demo.dtos.CreateJobRequest;
import com.example.demo.dtos.JobUpdateRequest;
import com.example.demo.dtos.PageDto;
import com.example.demo.exceptions.CommonsModuleException;
import com.example.demo.models.IUserDetails;
import com.example.demo.service.JobService;
import com.example.demo.specifications.JobSpecs;
import com.example.demo.utils.IAppendableReferenceUtils;
import com.example.demo.utils.PageRequestUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.lang.reflect.InvocationTargetException;

import static com.example.demo.enums.Authorities.RECRUITER_PREAUTHORIZE;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/job")
@CrossOrigin(origins = "*")
public class JobController {

    private final JobService jobService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize(RECRUITER_PREAUTHORIZE)
    public void createJob(Authentication authentication, @RequestBody @Valid CreateJobRequest createJobRequest) {
        long userId = IUserDetails.getId(authentication);
        jobService.createJob(userId, createJobRequest);
    }

    @GetMapping
    public PageDto getJob(@RequestParam(value = "q", required = false) String query,
                          @RequestParam int page,
                          @RequestParam int size) {
        JobSpecs jobSpecs = new JobSpecs(query);
        jobSpecs.setPage(page);
        jobSpecs.setSize(size);
        return jobService.getJobs(jobSpecs);
    }

    @GetMapping("/recruiter")
    public PageDto getJobsByRecruiter(Authentication authentication, @RequestParam int page,
                                   @RequestParam int size) {
        long userId = IUserDetails.getId(authentication);
        return jobService.getJobsByRecruiter(userId, PageRequestUtils.normalize(page, size));
    }

    @PatchMapping("update")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize(RECRUITER_PREAUTHORIZE)
    public void update(@RequestParam String jobId, @RequestBody JobUpdateRequest jobUpdateRequest) throws CommonsModuleException, InvocationTargetException, IllegalAccessException {
        long id = IAppendableReferenceUtils.getIdFrom(jobId);
        jobService.update(id, jobUpdateRequest);
    }
}
