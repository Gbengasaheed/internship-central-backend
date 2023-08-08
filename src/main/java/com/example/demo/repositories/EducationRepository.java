package com.example.demo.repositories;


import com.example.demo.models.Education;
import com.example.demo.models.Experience;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EducationRepository extends JpaRepository<Education, Long> {
    List<Education> findByUserId(Long userId);
}
