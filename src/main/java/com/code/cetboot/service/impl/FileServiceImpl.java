package com.code.cetboot.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.code.cetboot.bean.PageResult;
import com.code.cetboot.bean.Result;
import com.code.cetboot.entity.File;
import com.code.cetboot.service.FileService;
import com.code.cetboot.mapper.FileMapper;
import com.code.cetboot.util.FileUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

/**
* @author 19735
* @description 针对表【file】的数据库操作Service实现
* @createDate 2025-04-26 01:53:46
*/
@Service
public class FileServiceImpl extends ServiceImpl<FileMapper, File>
    implements FileService{

    @Value("${file.filePath}")
    private String filePath;

    @Value("${file.audioPath}")
    private String audioPath;

    @Value("${webPath.file}")
    private String fileWebPath;

    @Value("${webPath.audio}")
    private String audioWebPath;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Override
    public Result uploadFile(MultipartFile file, String fileName, Integer type) throws Exception {
        String path = type == 0 ? filePath : audioPath;
        String webPath = type == 0 ? fileWebPath : audioWebPath;
        java.io.File fileFolder = new java.io.File(path);
        if (!fileFolder.exists()){
            fileFolder.mkdirs();
        }
        String ext = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        String name = UUID.randomUUID().toString().replaceAll("-", "") + ext;
        String fileSavePath = path + name;
        java.io.File toFile = FileUtil.multipartFileToFile(file, fileSavePath);
        File fileInfo = new File();
        fileInfo.setName(fileName);
        fileInfo.setSrc(contextPath + webPath + name);
        if (type == 0) {
            boolean saved = save(fileInfo);
            if (!saved){
                toFile.delete();
                return Result.fail("文件上传失败");
            }
        }
        return Result.success("文件上传成功", fileInfo);
    }

    @Override
    public Result searchFile(Page<File> pageDto, String keyword) {
        Page<File> filePage = baseMapper.selectFileByKeyword(pageDto, keyword);
        return Result.success("获取文件列表成功", PageResult.of(filePage));
    }
}




