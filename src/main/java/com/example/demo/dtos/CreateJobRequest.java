package com.example.demo.dtos;

import com.example.demo.enums.JobType;
import com.example.demo.enums.Location;
import com.example.demo.enums.Status;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Date;

@Setter
@Getter
public class CreateJobRequest {

    private String title;

    private String description;

    private JobType type;

    private String country;

    private Location location;

    private Date endDate;

    private Status status;
}
