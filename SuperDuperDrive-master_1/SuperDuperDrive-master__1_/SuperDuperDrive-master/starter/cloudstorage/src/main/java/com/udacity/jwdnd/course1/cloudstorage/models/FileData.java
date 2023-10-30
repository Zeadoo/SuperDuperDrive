package com.udacity.jwdnd.course1.cloudstorage.models;

import java.sql.Blob;


public class FileData {
    private Blob fileData;

    public FileData(Blob fileData) {
        this.fileData = fileData;
    }
    public Blob getFileData() {
        return fileData;
    }

}