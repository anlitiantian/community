package com.mrliu.community.controller;

import cn.hutool.core.util.StrUtil;
import com.mrliu.community.dto.FileDTO;
import com.mrliu.community.util.QiNiuYunUtils;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * @program: community
 * @description:
 * @author: Mr.Liu
 * @create: 2022-05-14 16:10
 **/
@Controller
public class FileController {
    @Autowired
    private QiNiuYunUtils qiNiuYunUtils;

    @PostMapping("/file/uploadFile")
    @ResponseBody
    public FileDTO fileUpload(@RequestPart("upload_file") MultipartFile mf,
                              @RequestPart("upload_file") MultipartFile[] mfs) throws IOException {
        //需要返回的json:
        // {
        //  "success": true/false,
        //  "msg": "error message", # optional
        //  "file_path": "[real file path]"
        // }

        //图片上传七牛云
        //...生成上传凭证，然后准备上传
        InputStream inputStream = mf.getInputStream();
        String[] strings = mf.getOriginalFilename().split("\\.");
        String suffix = "";
        if(strings.length > 1){
            suffix = strings[strings.length - 1];
        }else {
            return new FileDTO(false, "文件名格式错误", "/images/Error_notification_SVG.png");
        }

        String uploadResult = qiNiuYunUtils.upload(inputStream, UUID.randomUUID().toString() + "." + suffix);
        String errorUpload = "上传七牛云出错";
        if(StrUtil.equals(errorUpload, uploadResult)){
            return new FileDTO(false, "文件名格式错误", "/images/Error_notification_SVG.png");
        }else {
            return new FileDTO(true, "上传成功", uploadResult);
        }
    }
}
