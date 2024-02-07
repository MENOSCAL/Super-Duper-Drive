package com.udacity.jwdnd.course1.cloudstorage.model;

import java.io.InputStream;

public class File {
    private int fileId;
    private int userId;
    private String filename;
    private String contentType;
    private String fileSize;
    private InputStream fileData;
    private String filePath;

    public File() {}

    public File(int fileId, int userId, String filename, String contentType, String fileSize, InputStream fileData, String filePath) {
        this.fileId = fileId;
        this.userId = userId;
        this.filename = filename;
        this.contentType = contentType;
        this.fileSize = fileSize;
        this.fileData = fileData;
        this.filePath = filePath;
    }

    public int getFileId() {
        return fileId;
    }

    public void setFileId(int fileId) {
        this.fileId = fileId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public InputStream getFileData() {
        return fileData;
    }

    public void setFileData(InputStream fileData) {
        this.fileData = fileData;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
