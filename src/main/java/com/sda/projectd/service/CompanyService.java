package com.sda.projectd.service;

import com.sda.projectd.model.Company;

import java.util.Collection;
import java.util.Optional;

public interface CompanyService {
    void addCompany(Company company);
    Collection<Company> findByName(String name);
    Optional<Company> findById(Long companyId);
}
