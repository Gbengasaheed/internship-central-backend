package com.example.demo.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SuperUserDto extends UserDto {

    List<EducationDto> educationDtoList;

    List<ExperienceDto> experienceDtoList;
}
