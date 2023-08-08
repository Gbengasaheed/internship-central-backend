package com.example.demo.repositories;

import com.example.demo.models.CV;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CvRepository extends JpaRepository<CV, Long> {
    Optional<CV> findByUserId(long userId);

    @Query(value = "select url from cvs where user_id =?1", nativeQuery = true)
    String findCvUrlByUserId(long userId);
}
