package com.example.demo.repositories;

import com.example.demo.models.Job;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface JobRepository extends CrudRepository<Job, Long>, JpaSpecificationExecutor<Job> {

    Page<Job> findByIdIn(List<Long> jobIds, Pageable pageable);

    Page<Job> findByUserId(long userId, Pageable normalize);
}
