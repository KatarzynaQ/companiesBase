package com.sda.projectd.service;

import com.sda.projectd.model.Address;
import com.sda.projectd.model.Company;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Stream;

@Component
public class CompanyServiceInMemoryImpl implements CompanyService {

    private Collection<Company> companies;

    public CompanyServiceInMemoryImpl() {
        this.companies = new ArrayList<>();
    }

    @Override
    public void addCompany(Company command) {
        command.setNewName(command.getNewName());
        companies.add(command);
    }

    @Override
    public Stream<Company> findByName(String name) {
        return companies.stream().filter(n->n.getNewName().equals(name));
    }

}
