package com.sda.projectd.model;

import com.sda.projectd.service.FileService;
import com.sda.projectd.service.FileServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.assertj.core.api.Java6Assertions.assertThat;

@SpringBootTest
@SpringJUnitConfig
public class FileServiceTest {

    private FileService fileService;

    @Autowired
    private GridFsOperations operations;

    @BeforeEach
    public void before() {
        this.fileService = new FileServiceImpl(operations);
    }
    @DisplayName("should upload file")
    @Test
    void test8() throws Exception {
        //given

        InputStream inputStream = new ByteArrayInputStream("hello".getBytes());
        //when
        String resultId = fileService.uploadFile(inputStream);
        //then
        InputStream foundFileContent = fileService.downloadFile(resultId);
        assertThat(foundFileContent).hasSameContentAs(inputStream);
    }
}
