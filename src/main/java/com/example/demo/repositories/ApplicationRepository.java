package com.example.demo.repositories;

import com.example.demo.dtos.projections.ApplicationProjection;
import com.example.demo.models.Application;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
    @Query(value = "SELECT CASE WHEN COUNT(*) > 0 THEN TRUE ELSE FALSE END FROM applications a WHERE a.user_id = ?1 AND a.job_id = ?2", nativeQuery = true)
    boolean existsByUserIdAndJobId(long userId, long jobId);


    @Query(value = "SELECT job_id FROM applications a WHERE a.user_id = ?1", nativeQuery = true)
    List<Long> findJobIdsByUserId(long userId);

    @Query(value = "SELECT user_id as userId, cv_url as cvUrl, created_At as createdAt FROM applications a WHERE a.job_id = ?1", nativeQuery = true)
    Page<ApplicationProjection> getApplicantDetails(long jobId, Pageable pageable);
}
