package com.udacity.jwdnd.course1.cloudstorage.controllers;


import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import com.udacity.jwdnd.course1.cloudstorage.models.File;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

@Controller
@RequestMapping("/file/download")
public class DownloadFIle {
    private final FileService downloadfileService;
    private final UserService user;

    public DownloadFIle( UserService user, FileService file) {
        this.user = user;
        this.downloadfileService = file;
    }


    @GetMapping
    @ResponseBody
    public ResponseEntity<InputStreamResource> downloadFile(@RequestParam("fileid") int fileId) throws SQLException, IOException {
        File file = this.downloadfileService.getFileUsingId(fileId);
        HttpHeaders httpHeaders = new HttpHeaders();

        User currUser = user.getUser(
                SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getName());

        if (!Objects.equals(currUser.getUserId(), file.getUserId())) {
            httpHeaders.add("Location", "/result?success=0");
            return ResponseEntity.status(HttpStatus.FOUND).headers(httpHeaders).build();
        }

        ByteArrayResource bytes = new ByteArrayResource(file.getFileData().getBinaryStream().readAllBytes());


        httpHeaders.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getFileName());
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(file.getContentType()))
                .headers(httpHeaders)
                .body(new InputStreamResource(bytes.getInputStream()));
    }
}
