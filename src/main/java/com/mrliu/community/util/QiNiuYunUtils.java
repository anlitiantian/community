package com.mrliu.community.util;

import com.alibaba.fastjson.JSON;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.InputStream;

/**
 * @program: community
 * @description:
 * @author: Mr.Liu
 * @create: 2022-05-14 21:08
 **/
@Component
public class QiNiuYunUtils {
    @Value("${qiniu.accessKey}")
    private String accessKey;
    @Value("${qiniu.secretKey}")
    private String secretKey;
    @Value("${qiniu.bucketName}")
    private String bucketName;
    @Value("${qiniu.path}")
    private String url;

    public String upload(InputStream file, String fileName) throws QiniuException {
        Auth auth = Auth.create(accessKey, secretKey);
        UploadManager uploadManager = new UploadManager(new Configuration(Region.region1()));
        String token = auth.uploadToken(bucketName);
        Response res = uploadManager.put(file, fileName, token, null, null);
        //解析上传成功的结果，暂时用不到
        DefaultPutRet defaultPutRet = JSON.parseObject(res.bodyString(), DefaultPutRet.class);
        if (!res.isOK()) {
            throw new RuntimeException("上传七牛云出错:" + res);
        }
        return url + "/" + fileName;
    }
}
