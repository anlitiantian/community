package com.mrliu.community.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: community
 * @description:
 * @author: Mr.Liu
 * @create: 2022-05-14 16:14
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileDTO {
    private boolean success;
    private String msg;
    private String file_path;

}
