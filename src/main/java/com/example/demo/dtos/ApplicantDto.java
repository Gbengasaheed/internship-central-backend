package com.example.demo.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class ApplicantDto {

    private String cvUrl;

    private String fullName;

    private String username;

    private Date appliedAt;
}
