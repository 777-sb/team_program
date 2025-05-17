package com.code.cetboot.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.code.cetboot.bean.Result;
import com.code.cetboot.entity.File;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

/**
* @author 19735
* @description 针对表【file】的数据库操作Service
* @createDate 2025-04-26 01:53:46
*/
public interface FileService extends IService<File> {

    /**
     * 上传文件
     *
     * @param file 文件
     * @param fileName 文件名
     * @param type 文件类型
     * @return 结果
     */
    Result uploadFile(MultipartFile file, String fileName, Integer type) throws Exception;

    /**
     * 分页查询文件
     *
     * @param pageDto 分页参数
     * @param keyword 关键字
     * @return 分页结果
     */
    Result searchFile(Page<File> pageDto, String keyword);
}
