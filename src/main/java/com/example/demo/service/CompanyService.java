package com.example.demo.service;

import com.example.demo.dtos.CompanyDto;
import com.example.demo.models.Company;
import com.example.demo.repositories.CompanyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;

    public void addCompany(long userId, CompanyDto companyDto) {
        Company company = new Company();
        BeanUtils.copyProperties(companyDto, company);
        company.setUserId(userId);
        companyRepository.save(company);
    }
}
