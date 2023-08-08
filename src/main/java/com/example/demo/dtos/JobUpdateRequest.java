package com.example.demo.dtos;

import com.example.demo.enums.JobType;
import com.example.demo.enums.Location;
import com.example.demo.enums.Status;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class JobUpdateRequest {
    private String title;

    private String description;

    private String country;

    private JobType type;

    private Location location;

    private Date endDate;

    private Status status;
}
