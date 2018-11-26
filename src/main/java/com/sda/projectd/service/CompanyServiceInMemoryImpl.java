package com.sda.projectd.service;

import com.sda.projectd.model.Company;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

// @Component
public class CompanyServiceInMemoryImpl {

    private Collection<Company> companies;

    public CompanyServiceInMemoryImpl() {
        this.companies = new ArrayList<>();
    }

    public void addCompany(Company command) {
        command.setCurrentName(command.getCurrentName());
        companies.add(command);
    }

    public Collection<Company> findByName(String name) {
        return companies.stream().filter(n->n.getNames().contains(name)).collect(Collectors.toList());
    }
}
