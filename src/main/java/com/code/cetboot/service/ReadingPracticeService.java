package com.code.cetboot.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.code.cetboot.bean.Result;
import com.code.cetboot.dto.AddReadingDTO;
import com.code.cetboot.dto.ReadingPracticeDTO;
import com.code.cetboot.entity.ReadingPractice;
import com.baomidou.mybatisplus.extension.service.IService;
import com.code.cetboot.vo.reading.ReadingRecordsVO;

/**
* @author 19735
* @description 针对表【reading_practice】的数据库操作Service
* @createDate 2025-04-26 01:53:46
*/
public interface ReadingPracticeService extends IService<ReadingPractice> {

    /**
     * 分页查询阅读练习题
     *
     * @param pageDto 分页参数
     * @param keyword 关键字
     * @return 分页结果
     */
    Result getPractices(Page<ReadingPractice> pageDto, String keyword);

    /**
     * 根据ID查询阅读练习题
     *
     * @param practiceId 练习题ID
     * @return 练习题详情
     */
    Result getPractice(Integer practiceId);

    /**
     * 提交阅读练习题
     *
     * @param readingPracticeDTO 提交的练习题数据
     * @return 提交结果
     */
    Result submit(ReadingPracticeDTO readingPracticeDTO);

    /**
     * 分页查询阅读记录
     *
     * @param pageDto 分页参数
     * @return 分页结果
     */
    Result getRecords(Page<ReadingRecordsVO> pageDto);

    /**
     * 根据ID查询阅读记录
     *
     * @param readingRecordId 阅读记录ID
     * @return 阅读记录详情
     */
    Result getRecord(Integer readingRecordId);

    /**
     * 添加阅读练习题
     * @param addReadingDTO 阅读练习题数据传输对象
     * @return 添加结果
     */
    Result add(AddReadingDTO addReadingDTO);
}
