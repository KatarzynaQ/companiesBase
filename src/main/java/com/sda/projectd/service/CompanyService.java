package com.sda.projectd.service;

import com.sda.projectd.model.Company;
import org.bson.types.ObjectId;

import javax.print.DocFlavor;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Optional;

public interface CompanyService {
    void updateCompany(Long id, Company company) throws CompanyDoesntExistException;
    Company addCompany(Company company) throws CompanyAlreadyExistsException;
    Company addCompany(Company company, InputStream file) throws CompanyAlreadyExistsException;
    Collection<Company> findByName(String name);
    Optional<Company> findById(Long companyId);
}
