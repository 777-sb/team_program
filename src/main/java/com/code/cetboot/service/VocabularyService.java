package com.code.cetboot.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.code.cetboot.bean.Result;
import com.code.cetboot.dto.AddVocabularyDTO;
import com.code.cetboot.dto.VocabularyPracticeDTO;
import com.code.cetboot.entity.Vocabulary;
import com.baomidou.mybatisplus.extension.service.IService;
import com.code.cetboot.vo.vocabulary.VocabularyRecordsVO;

import java.util.List;

/**
* @author 19735
* @description 针对表【vocabulary】的数据库操作Service
* @createDate 2025-04-26 01:53:46
*/
public interface VocabularyService extends IService<Vocabulary> {

    /**
     * 获取词汇练习
     * @param count 词汇练习数量
     * @return Result
     */
    Result getPractices(Integer count);

    /**
     * 提交词汇练习
     * @param vocabularyPracticeList 词汇练习列表
     * @return Result
     */
    Result submit(List<VocabularyPracticeDTO> vocabularyPracticeList);

    /**
     * 获取词汇记录
     * @param pageDto 分页参数
     * @return Result
     */
    Result getRecords(Page<VocabularyRecordsVO> pageDto);

    /**
     * 根据ID获取词汇记录
     * @param addVocabularyDTOList 词汇记录列表
     * @return Result
     */
    Result add(List<AddVocabularyDTO> addVocabularyDTOList);
}
