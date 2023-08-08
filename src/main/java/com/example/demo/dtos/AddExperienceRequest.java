package com.example.demo.dtos;

import com.example.demo.enums.EmploymentType;
import com.example.demo.enums.Location;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class AddExperienceRequest {
    private String title;

    private EmploymentType employmentType;

    private String companyName;

    private Location location;

    private String description;

    private Date startDate;

    private Date endDate;
}
