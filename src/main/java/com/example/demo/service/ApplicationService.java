package com.example.demo.service;

import com.example.demo.dtos.ApplicantDto;
import com.example.demo.dtos.ApplicationDto;
import com.example.demo.dtos.PageDto;
import com.example.demo.dtos.UserDto;
import com.example.demo.dtos.projections.ApplicationProjection;
import com.example.demo.exceptions.CommonsModuleException;
import com.example.demo.models.Application;
import com.example.demo.models.Job;
import com.example.demo.repositories.ApplicationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ApplicationService {

    private final ApplicationRepository applicationRepository;

    private final CvService cvService;

    private final JobService jobService;

    private final UserService userService;


    public void apply(long userId, long jobId) throws CommonsModuleException {
        if (hasAppliedBefore(userId, jobId)) return;
        jobService.jobExist(jobId);
        String url = cvService.getUrl(userId);
        Application application = new Application();
        application.setCvUrl(url);
        application.setUserId(userId);
        application.setJobId(jobId);
        applicationRepository.save(application);
    }

    private boolean hasAppliedBefore(long userId, long jobId) {
        return applicationRepository.existsByUserIdAndJobId(userId, jobId);
    }

    public PageDto getApplications(long userId, Pageable pageable) {
        List<Long> jobIds = applicationRepository.findJobIdsByUserId(userId);
        Page<Job> pagedJobs = jobService.getJobsByUserId(jobIds, pageable);
        List<ApplicationDto> applicationDtos = pagedJobs.stream().map(job -> {
            ApplicationDto applicationDto = new ApplicationDto();
            applicationDto.setJobId(job.getReference());
            applicationDto.setJobTitle(job.getTitle());
            applicationDto.setAppliedOn(job.getCreatedAt());
            return applicationDto;
        }).collect(Collectors.toList());
        return PageDto.build(pagedJobs, applicationDtos);
    }

    public PageDto getApplicants(long jobId, Pageable pageable) {
        Page<ApplicationProjection> projections = applicationRepository.getApplicantDetails(jobId, pageable);
        List<ApplicantDto> dtos = projections.stream().map(projection -> {
                    ApplicantDto applicantDto = new ApplicantDto();
                    applicantDto.setCvUrl(projection.getCvUrl());
                    applicantDto.setAppliedAt(projection.getCreatedAt());
                    try {
                        UserDto userDto = userService.findUserById(projection.getUserId());
                        applicantDto.setFullName(userDto.getFirstName() + " " + userDto.getLastName());
                        applicantDto.setUsername(userDto.getUsername());
                    } catch (CommonsModuleException e) {
                        log.error("An error occurred for this user [{}]", projection.getUserId());
                    }
                    return applicantDto;
        }).toList();
        return PageDto.build(projections, dtos);
    }
}
