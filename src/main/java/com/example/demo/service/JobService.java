package com.example.demo.service;

import com.example.demo.dtos.CreateJobRequest;
import com.example.demo.dtos.JobDto;
import com.example.demo.dtos.JobUpdateRequest;
import com.example.demo.dtos.PageDto;
import com.example.demo.exceptions.CommonsModuleException;
import com.example.demo.models.Job;
import com.example.demo.repositories.JobRepository;
import com.example.demo.specifications.JobSpecs;
import com.example.demo.utils.BeanUtilHelper;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

@RequiredArgsConstructor
@Service
public class JobService {

    private final JobRepository jobRepository;


    public void createJob(long userId, CreateJobRequest createJobRequest) {
        Job job = new Job();
        BeanUtils.copyProperties(createJobRequest, job);
        job.setUserId(userId);
        jobRepository.save(job);

    }

    public PageDto getJobs(JobSpecs jobSpecs) {
        Page<Job> jobPage = jobRepository.findAll(jobSpecs, jobSpecs.getPageable());
        return PageDto.build(jobPage, JobDto::toDto);
    }

    public void update(long id, JobUpdateRequest jobUpdateRequest) throws CommonsModuleException, InvocationTargetException, IllegalAccessException {
        Job job = jobRepository.findById(id).orElseThrow(() -> new CommonsModuleException("does.not.exist", HttpStatus.NOT_FOUND));
        BeanUtilHelper.copyPropertiesIgnoreNull(jobUpdateRequest, job);
        jobRepository.save(job);
    }

    public void jobExist(long id) throws CommonsModuleException {
        jobRepository.findById(id).orElseThrow(() -> new CommonsModuleException("does.not.exist", HttpStatus.NOT_FOUND));
    }

    public Page<Job> getJobsByUserId(List<Long> jobIds, Pageable pageable) {
        return jobRepository.findByIdIn(jobIds, pageable);
    }

    public PageDto getJobsByRecruiter(long userId, Pageable normalize) {
        Page<Job> jobPage = jobRepository.findByUserId(userId,normalize);
        return PageDto.build(jobPage, JobDto::toDto);
    }
}
