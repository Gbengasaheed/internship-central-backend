package com.example.demo.service;

import com.example.demo.dtos.AddEducationRequest;
import com.example.demo.dtos.EducationDto;
import com.example.demo.dtos.UpdateEducationRequest;
import com.example.demo.exceptions.CommonsModuleException;
import com.example.demo.models.Education;
import com.example.demo.repositories.EducationRepository;
import com.example.demo.utils.BeanUtilHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EducationService {
    private  final EducationRepository educationRepository;
    public void addEducation(long userId, AddEducationRequest addEducationRequest) {
        Education education = new Education();
        BeanUtils.copyProperties(addEducationRequest,education);
        education.setUserId(userId);
        educationRepository.save(education);
    }

    public List<EducationDto> getUserEducations(long userId) {
        List<Education> educations = educationRepository.findByUserId(userId);
        return educations.stream().map(education -> {
            EducationDto educationDto  = new EducationDto();
            BeanUtils.copyProperties(education,educationDto);
            return educationDto;
        }).toList();
    }

    public void update(long id, UpdateEducationRequest updateEducationRequest) throws CommonsModuleException, InvocationTargetException, IllegalAccessException {
        Education education = educationRepository.findById(id).orElseThrow(() -> new CommonsModuleException("does.not.exist", HttpStatus.NOT_FOUND));
        BeanUtilHelper.copyPropertiesIgnoreNull(updateEducationRequest, education);
        educationRepository.save(education);
    }
}
