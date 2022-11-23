package com.kun.controller;

import com.kun.domain.Result;
import com.kun.service.UploadService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * @author kun
 * @since 2022-11-22 22:06
 */
@RestController
public class UploadController {

    @Resource
    private UploadService uploadService;

    @PostMapping("/upload")
    public Result uploadImg(@RequestParam("img")MultipartFile file) {
        try {
            return uploadService.uploadImg(file);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("文件上传失败!!!!");
        }
    }
}
