package com.example.demo.dtos;

import com.example.demo.enums.JobType;
import com.example.demo.enums.Location;
import com.example.demo.enums.Status;
import com.example.demo.models.Job;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.util.Date;

@Setter
@Getter
public class JobDto {

    private String title;

    private String description;

    private JobType type;

    private Location location;

    private Date endDate;

    private Status status;

    private String reference;


    public static JobDto fromModel(Job job){
        JobDto jobDto = new JobDto();
        BeanUtils.copyProperties(job, jobDto);
        jobDto.setReference(jobDto.getReference());
        return jobDto;
    }


    public static JobDto toDto(Object o) {
        return fromModel((Job) o);
    }
}
