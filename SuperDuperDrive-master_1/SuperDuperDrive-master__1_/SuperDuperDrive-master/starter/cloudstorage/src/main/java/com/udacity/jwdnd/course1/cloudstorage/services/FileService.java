package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.FileInfo;
import com.udacity.jwdnd.course1.cloudstorage.models.FileData;
import com.udacity.jwdnd.course1.cloudstorage.models.File;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;


@Service
public class FileService {
    private List<FileInfo> fileList;
    private final FileMapper fileMapper;


    public FileService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    public FileData getFileDataFromMultipartFile(MultipartFile file) throws IOException, SQLException {
        return new FileData(new SerialBlob(file.getBytes()));
    }

    public List<FileInfo> getFileAsList(int userId) {
        if (fileList == null || fileList.isEmpty()) {
            setFileList(userId);
            return fileList;
        }

        AtomicInteger uId = new AtomicInteger();
        this.fileList.stream().peek(f -> uId.set(f.getUserId()));
        if (uId.get() == userId) {
            return fileList;
        } else {
            setFileList(userId);
        }
        return fileList;
    }

    public void setFileList(int userId) {
        fileList = fileMapper.getAllFilesFromUserId(userId);
    }
    public File getFileUsingId(int fileId) {
        return fileMapper.getOneFileInfo(fileId);
    }

    public void uploadFile(MultipartFile file, int userId) throws SQLException, IOException {

        if (file.isEmpty()) {
            throw new FileUploadException("6");
        }
        getFileAsList(userId);
        List<String> fileNamesForUser = fileList.stream()
                .map(FileInfo::getFileName)
                .collect(Collectors.toList());
        if (fileNamesForUser.contains(file.getOriginalFilename())) {
            throw new FileAlreadyExistsException("2");
        }

        FileData fileData = getFileDataFromMultipartFile(file);
        FileInfo fileInfo = getFileInfoFromMultipartFile(file, userId);


        fileMapper.insert(fileInfo);

        int fileId = fileMapper.getFileId(fileInfo);


        fileMapper.updateFileData(fileData.getFileData(), fileId);
    }

    public FileInfo getFileInfoFromMultipartFile(MultipartFile file, int userId) throws IOException, SQLException {

        return new FileInfo(null, file.getOriginalFilename(), file.getContentType(), file.getSize(), userId);
    }

    public void deleteFileFromMapper(int fileId) {
        fileMapper.deleteFile(fileId);
    }
}
