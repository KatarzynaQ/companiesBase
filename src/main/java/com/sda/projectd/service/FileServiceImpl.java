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


    private final GridFsOperations operations;

    public FileServiceImpl(GridFsOperations operations) {
        this.operations = operations;
    }

    /**
     * @param inputStream
     * @return
     * fileId
     * @throws IOException
     */
    @Override
    public String uploadFile(InputStream inputStream) throws IOException {
            ObjectId fileId = operations.store(inputStream, "default-file");
            return fileId.toString();
    }

    @Override
    public InputStream downloadFile(String fileId) throws IOException {
        Query query1 = new Query();
        query1.addCriteria(Criteria.where("_id").is(new ObjectId(fileId)));
        return new GridFsResource(operations.findOne(query1)).getInputStream();
    }
}
