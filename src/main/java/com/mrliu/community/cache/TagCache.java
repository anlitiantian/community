package com.mrliu.community.cache;

import cn.hutool.core.util.StrUtil;
import com.mrliu.community.dto.TagDTO;
import com.sun.org.apache.bcel.internal.classfile.Code;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @program: community
 * @description: 将标签属性存在java里
 * @author: Mr.Liu
 * @create: 2022-05-08 20:11
 **/
public class TagCache {
    public static List<TagDTO> get(){
        List<TagDTO> tagDTOS = new ArrayList<>();
        TagDTO program = new TagDTO();
        program.setCategoryName("开发语言");
        program.setTags(Arrays.asList("Java","C++","C语言","net","C#","VB(Visual Basic)","VFP（Visual FoxPro ）","ASP","JSP","Python","PHP","PERL","Ruby","Ada","Lisp"));

        TagDTO framework = new TagDTO();
        framework.setCategoryName("数据框架");
        framework.setTags(Arrays.asList("Hadoop","Spark","Storm","Samza"));

        TagDTO db = new TagDTO();
        db.setCategoryName("数据库");
        db.setTags(Arrays.asList("MySQL","MariaDB","Percona Server","PostgreSQL","Microsoft Access","Microsoft SQL Server","Google Fusion Tables"
                ,"FileMaker","Oracle数据库","Sybase","dBASE","Clipper","FoxPro","foshub"));

        TagDTO tools = new TagDTO();
        tools.setCategoryName("开发工具");
        tools.setTags(Arrays.asList("IntelliJ IDEA","Visual Studio Code","SwitchHosts","FinalShell","Navicat Premium"));
        tagDTOS.add(program);
        tagDTOS.add(framework);
        tagDTOS.add(db);
        tagDTOS.add(tools);
        return tagDTOS;
    }

    public static String filterInvalid(String tags){
        List<String> split = StrUtil.split(tags, ",");
        List<TagDTO> tagDTOS = get();

        List<String> tagList = tagDTOS.stream().flatMap(tag -> tag.getTags().stream()).collect(Collectors.toList());
        String invalid = split.stream().filter(t -> !tagList.contains(t)).collect(Collectors.joining(","));
        return invalid;
    }
}
