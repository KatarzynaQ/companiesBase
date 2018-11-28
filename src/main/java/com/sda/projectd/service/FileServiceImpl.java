package com.sda.projectd.service;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
@Service
public class FileServiceImpl implements FileService {
    CompanyService companyService;
    private GridFsOperations operations;

    @Autowired
    public FileServiceImpl(CompanyService companyService, GridFsOperations operations) {
        this.companyService = companyService;
        this.operations = operations;
    }

    @Override
    public String uploadFile(InputStream inputStream, Long companyId) throws IOException {
        return companyService.findById(companyId).map(company -> {
            ObjectId id = operations.store(inputStream, "default-file");
            company.getFiles().add(id.toString());
            companyService.updateCompany(companyId, company);
            return id.toString();
        }).orElseThrow(() ->
                new CompanyDoesntExistException(String.format("Failed to upload a file. Company %s doesn't exist.", companyId.toString())));
    }

    @Override
    public InputStream downloadFile(String fileId) throws IOException {
        Query query1 = new Query();
        query1.addCriteria(Criteria.where("_id").is(new ObjectId(fileId)));
        return new GridFsResource(operations.findOne(query1)).getInputStream();
    }
}
