package com.example.demo.dtos;

import com.example.demo.enums.Degree;
import com.example.demo.enums.EmploymentType;
import com.example.demo.enums.FieldOfStudy;
import com.example.demo.enums.Location;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class EducationDto {
    private String school;

    private Degree degree;

    private FieldOfStudy fieldOfStudy;

    private Date startDate;

    private Date endDate;
}
