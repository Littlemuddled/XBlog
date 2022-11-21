package com.kun.service;

import com.kun.domain.Result;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author kun
 * @since 2022-11-20 20:07
 */
public interface UploadService {
    Result uploadImg(MultipartFile img);
}
