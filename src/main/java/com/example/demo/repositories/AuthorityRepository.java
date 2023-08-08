package com.example.demo.repositories;

import com.example.demo.models.IAuthorities;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorityRepository extends JpaRepository<IAuthorities, Long> {

}
