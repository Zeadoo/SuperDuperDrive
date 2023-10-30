package com.udacity.jwdnd.course1.cloudstorage.models;

public class FileInfo {
    private Integer fileId;
    private String fileName;
    private String contentType;
    private long fileSize;
    private Integer userId;

    public FileInfo(Integer fileId, String fileName, String contentType, long fileSize, Integer userId) {
        this.fileId = fileId;
        this.fileName = fileName;
        this.contentType = contentType;
        this.fileSize = fileSize;
        this.userId = userId;
    }
    public Integer getFileId() {return fileId;}
    public void setFileId(Integer fileId) {this.fileId = fileId;}
    public String getFileName() {
        return fileName;
    }
    public Integer getUserId() {
        return userId;
    }

}
