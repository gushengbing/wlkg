package com.wlkg.controller;

import com.wlkg.service.UploadService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class UploadController {

    @Autowired
    private UploadService uploadService;

    @PostMapping("/upload/image")
    public ResponseEntity<String> uploadImage(@RequestParam("file")MultipartFile file){ //MultipartFile 上传文件类型
        String url=uploadService.upload(file);
        System.out.println("添加图片的url:"+url);
        if (StringUtils.isBlank(url)){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

        return ResponseEntity.ok(url);
    }

}
