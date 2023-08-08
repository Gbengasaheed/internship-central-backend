package com.example.demo.dtos;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Setter
@Getter
public class CompanyDto {

    @Size(max = 30, min = 2, message = "{company.accepted_length}")
    @NotBlank(message = "{company.not_blank}")
    private String name;

    private Integer size;

    @Size(max = 20, min = 2, message = "{industry.accepted_length}")
    @NotBlank(message = "{industry.not_blank}")
    private String industry;

    @Size(max = 20, min = 2, message = "{location.accepted_length}")
    @NotBlank(message = "{location.not_blank}")
    private String location;
}
