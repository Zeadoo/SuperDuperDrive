package com.udacity.jwdnd.course1.cloudstorage.models;

import java.sql.Blob;


public class File {
    private Integer fileId;
    private String fileName;
    private String contentType;
    private long fileSize;
    private Integer userId;
    private Blob fileData;

    public File(Integer fileId, String fileName, String contentType, long fileSize, Integer userId, Blob fileData) {
        this.fileId = fileId;
        this.fileName = fileName;
        this.contentType = contentType;
        this.fileSize = fileSize;
        this.userId = userId;
        this.fileData = fileData;
    }

    public String getFileName() {
        return fileName;
    }
    public String getContentType() {
        return contentType;
    }
    public Integer getUserId() {
        return userId;
    }
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    public Blob getFileData() {
        return fileData;
    }

}
