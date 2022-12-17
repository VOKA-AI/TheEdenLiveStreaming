package com.live.web.controller;

import com.live.service.AmazonServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/storage")
public class RestS3Controller {

    @Autowired
    private AmazonServiceImpl amazonServiceImpl;

    @PostMapping("/uploadCorver")
    public String uploadFile(@RequestPart(value = "file") MultipartFile file) throws IOException {
        return this.amazonServiceImpl.uploadFile(file);
    }

    @DeleteMapping("/deleteFile")
    public String deleteFile(@RequestPart(value = "url") String fileUrl) {
        return this.amazonServiceImpl.deleteFileFromS3Bucket(fileUrl);
    }

    @GetMapping("/getFileList")
    public List<String> getFileList() {
//        return this.amazonService.listFiles();
        return null;
    }


}