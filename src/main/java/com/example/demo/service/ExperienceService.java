package com.example.demo.service;

import com.example.demo.dtos.AddExperienceRequest;
import com.example.demo.dtos.ExperienceDto;
import com.example.demo.dtos.UpdateExperienceRequest;
import com.example.demo.exceptions.CommonsModuleException;
import com.example.demo.models.Experience;
import com.example.demo.repositories.ExperienceRepository;
import com.example.demo.utils.BeanUtilHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExperienceService {
    private final ExperienceRepository experienceRepository;

    public void addExperience(long userId, AddExperienceRequest addExperienceRequest) {
        Experience experience = new Experience();
        BeanUtils.copyProperties(addExperienceRequest, experience);
        experience.setUserId(userId);
        experienceRepository.save(experience);
    }

    public List<ExperienceDto> getUserExperiences(long userId) {
        List<Experience> experiences = experienceRepository.findByUserId(userId);
        return experiences.stream().map(experience -> {
            ExperienceDto experienceDto  = new ExperienceDto();
            BeanUtils.copyProperties(experience,experienceDto);
            return experienceDto;
        }).toList();
    }

    public void update(long id, UpdateExperienceRequest updateExperienceRequest) throws CommonsModuleException, InvocationTargetException, IllegalAccessException {
        Experience experience = experienceRepository.findById(id).orElseThrow(() -> new CommonsModuleException("does.not.exist", HttpStatus.NOT_FOUND));
        BeanUtilHelper.copyPropertiesIgnoreNull(updateExperienceRequest, experience);
        experienceRepository.save(experience);
    }
}
