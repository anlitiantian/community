package com.mrliu.community.dto;

import lombok.Data;

import java.util.List;

/**
 * @program: community
 * @description: 大类标签
 * @author: Mr.Liu
 * @create: 2022-05-08 20:06
 **/
@Data
public class TagDTO {
    private String categoryName;
    private List<String> tags;
}
