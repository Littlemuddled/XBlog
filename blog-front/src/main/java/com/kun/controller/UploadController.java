package com.kun.controller;

import com.kun.domain.Result;
import com.kun.service.UploadService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * @author kun
 * @since 2022-11-20 20:00
 */
@RestController
public class UploadController {

    @Resource
    private UploadService uploadService;

    @PostMapping("/upload")
    public Result uploadImg(MultipartFile img) {
        return uploadService.uploadImg(img);
    }

}
