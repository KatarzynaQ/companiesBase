package com.sda.projectd.service;

import com.mongodb.QueryBuilder;
import com.sda.projectd.model.Company;
import com.sda.projectd.repository.CompanyRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.CriteriaDefinition;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;

@Service
public class CompanyServiceImpl implements CompanyService {
    private CompanyRepository companyRepository;
    private FileService fileService;

    @Autowired
    public CompanyServiceImpl(CompanyRepository companyRepository, FileService fileService) {
        this.companyRepository = companyRepository;
        this.fileService = fileService;
    }

    @Override
    public void updateCompany(Long id, Company company) throws CompanyDoesntExistException {
        findById(id).map(oldCompany -> {
            oldCompany.setCurrentName(company.getCurrentName());
            oldCompany.setAddress(company.getAddress());
            if (company.getKrs() != null) {
                oldCompany.setKrs(company.getKrs());
            }
            oldCompany.setNip(company.getNip());
            oldCompany.setRegon(company.getRegon());
            oldCompany.setAddress(company.getAddress());
            // repeat for all the other properties you want to update from the company object
            companyRepository.save(oldCompany);
            return oldCompany;
        }).orElseThrow(() -> new CompanyDoesntExistException("Nie ma takiej firmy"));
    }

    @Override
    public Company addCompany(Company company) throws CompanyAlreadyExistsException {
        if (company.getId() != null) {
            throw new CompanyAlreadyExistsException("Firma już istnieje");
        } else
            return companyRepository.
                    save(company);
    }

    @Override
    public Company addCompany(Company company, InputStream file) throws CompanyAlreadyExistsException {
        String newFileId = null;
        try {
            newFileId = fileService.uploadFile(file);
            company.getFiles().add(newFileId);
            return addCompany(company);
        } catch (IOException e) {
            throw new ProjectDException(String.format("Failed to upload file to company %s", company.getId()));
        }
    }

    @Override
    public Collection<Company> findByName(String name) {
        return companyRepository.findByNames(name);
    }

    @Override
    public Optional<Company> findById(Long companyId) {
        return companyRepository.findById(companyId);
    }

}
