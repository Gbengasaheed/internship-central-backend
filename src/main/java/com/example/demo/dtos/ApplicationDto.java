package com.example.demo.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class ApplicationDto {

    private String jobId;

    private String jobTitle;

    private Date appliedOn;

}
