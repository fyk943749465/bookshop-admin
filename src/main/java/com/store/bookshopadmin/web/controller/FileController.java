package com.store.bookshopadmin.web.controller;

import com.store.bookshopadmin.dto.FileInfo;

import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import sun.nio.ch.IOUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Date;

@RestController
@RequestMapping("/file")
public class FileController {

    @PostMapping("/upload")
    public FileInfo upload(MultipartFile file) throws IOException {
        System.out.println(file.getContentType());
        System.out.println(file.getName());
        System.out.println(file.getOriginalFilename());
        System.out.println(file.getSize());
        String path = "/Users/ender/myrepository/bookshop-admin/src/main/resources";
        String extention = StringUtils.substringAfterLast(file.getOriginalFilename(), ".");
        File localfile =new File(path, new Date().getTime() + "." + extention);
        file.transferTo(localfile);
        return new FileInfo(localfile.getAbsolutePath());
    }

    @GetMapping("/download")
    public void download(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String filePath = "/Users/ender/myrepository/bookshop-admin/src/main/resources/1591501021472.txt";
        try (InputStream inputStream = new FileInputStream(filePath);
             OutputStream outputStream = response.getOutputStream()) {
            response.setContentType("application/x-download");
            response.addHeader("Content-Disposition", "attachment;filename=text.txt");

            IOUtils.copy(inputStream, outputStream);
            outputStream.flush();
        }
    }
}
