package com.kun.service.impl;

import com.google.gson.Gson;
import com.kun.domain.Result;
import com.kun.enums.AppHttpCodeEnum;
import com.kun.exception.SystemException;
import com.kun.service.UploadService;
import com.kun.utils.PathUtil;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

/**
 * @author kun
 * @since 2022-11-20 20:08
 */
@Service
public class UploadServiceImpl implements UploadService {


    @Override
    public Result uploadImg(MultipartFile img) {
        String filename = img.getOriginalFilename();
        if (!(filename.toLowerCase().endsWith(".jpg") || filename.toLowerCase().endsWith(".jpeg") || filename.toLowerCase().endsWith(".png"))) {
            throw new SystemException(AppHttpCodeEnum.FILE_TYPE_ERROR);
        }
        String path = PathUtil.generateFilePath(filename);
        String url = this.uploadOSS(img, path);
        return Result.okResult(url);
    }

    private String uploadOSS(MultipartFile imgFile, String path) {
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.autoRegion());
        cfg.resumableUploadAPIVersion = Configuration.ResumableUploadAPIVersion.V2;// 指定分片上传版本
        //...其他参数参考类注释

        UploadManager uploadManager = new UploadManager(cfg);
        //...生成上传凭证，然后准备上传
        String accessKey = "XhWnDN6cogf8UsaAdcR6dZcvIu3zzvGxEo5uaytG";
        String secretKey = "GFvAqbfrokb74aTxCJmwk11aM1cHZJt0efmDcnid";
        String bucket = "xing-blog";

        //默认不指定key的情况下，以文件内容的hash值作为文件名
        try {
            InputStream is = imgFile.getInputStream();
            Auth auth = Auth.create(accessKey, secretKey);
            String upToken = auth.uploadToken(bucket);
            try {
                Response response = uploadManager.put(is, path, upToken,null,null);
                //解析上传成功的结果
                DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
                System.out.println(putRet.key);
                System.out.println(putRet.hash);
                return "http:rln6ut2dv.hn-bkt.clouddn.com/" + path;
            } catch (QiniuException ex) {
                Response r = ex.response;
                System.err.println(r.toString());
                try {
                    System.err.println(r.bodyString());
                } catch (QiniuException ex2) {
                    //ignore
                }
            }
        } catch (Exception ex) {
            //ignore
        }
        return null;
    }
}
