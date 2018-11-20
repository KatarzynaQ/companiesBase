package com.sda.projectd.service;

import com.sda.projectd.model.Address;
import com.sda.projectd.model.Company;

import java.util.ArrayList;
import java.util.Collection;

public class CompanyServiceInMemoryImpl implements CompanyService {

    private Collection<Company> companies;

    public CompanyServiceInMemoryImpl() {
        this.companies = new ArrayList<>();
    }

    @Override
    public void addCompany(Company command) {
        command.addName(command.getNewName());
        companies.add(command);
    }

    @Override
    public Collection<Company> findAll() {
        return companies;
    }
}
