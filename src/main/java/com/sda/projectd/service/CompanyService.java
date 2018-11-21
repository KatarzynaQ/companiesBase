package com.sda.projectd.service;

import com.sda.projectd.model.Company;

import java.util.Optional;
import java.util.stream.Stream;

public interface CompanyService {
    void addCompany(Company company);
    Stream<Company> findByName(String name);
}
