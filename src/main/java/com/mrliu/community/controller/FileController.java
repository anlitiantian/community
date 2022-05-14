package com.mrliu.community.controller;

import com.mrliu.community.dto.FileDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @program: community
 * @description:
 * @author: Mr.Liu
 * @create: 2022-05-14 16:10
 **/
@Controller
public class FileController {

    @PostMapping("/file/uploadFile")
    @RequestMapping("")
    @ResponseBody
    public FileDTO fileUpload(){
        //需要返回的json:
        // {
        //  "success": true/false,
        //  "msg": "error message", # optional
        //  "file_path": "[real file path]"
        // }

        FileDTO fileDTO = new FileDTO();
        fileDTO.setSuccess(true);
        fileDTO.setMsg("上传成功");
        fileDTO.setFile_path("/images/avatar.png");
        return fileDTO;
    }
}
