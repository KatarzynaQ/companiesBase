package com.sda.projectd.service;

import java.io.IOException;
import java.io.InputStream;

public interface FileService {
    String uploadFile(InputStream inputStream) throws IOException;
    InputStream downloadFile(String fileId) throws IOException;
}
