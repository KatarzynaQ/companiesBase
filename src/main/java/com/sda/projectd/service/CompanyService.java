package com.sda.projectd.service;

import com.sda.projectd.model.Company;

import java.util.Collection;

public interface CompanyService {
    void addCompany(Company company);
    Collection<Company> findByName(String name);
}
