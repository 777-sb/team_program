package com.code.cetboot.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.code.cetboot.bean.PageResult;
import com.code.cetboot.bean.Result;
import com.code.cetboot.dto.AddVocabularyDTO;
import com.code.cetboot.dto.VocabularyPracticeDTO;
import com.code.cetboot.entity.Vocabulary;
import com.code.cetboot.entity.VocabularyRecord;
import com.code.cetboot.mapper.VocabularyRecordMapper;
import com.code.cetboot.service.VocabularyRecordService;
import com.code.cetboot.service.VocabularyService;
import com.code.cetboot.mapper.VocabularyMapper;
import com.code.cetboot.vo.vocabulary.VocabularyRecordsVO;
import com.code.cetboot.vo.vocabulary.VocabularyVO;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
* @author 19735
* @description 针对表【vocabulary】的数据库操作Service实现
* @createDate 2025-04-26 01:53:46
*/
@Service
public class VocabularyServiceImpl extends ServiceImpl<VocabularyMapper, Vocabulary>
    implements VocabularyService{

    @Resource
    private VocabularyRecordService vocabularyRecordService;

    @Resource
    private VocabularyRecordMapper vocabularyRecordMapper;

    @Resource
    private Logger logger;

    @Override
    public Result getPractices(Integer count) {
        if (count <= 0) {
            return Result.fail("获取词汇练习失败");
        }
        List<Vocabulary> vocabularies = baseMapper.selectBatchRandom(count * 4);
        int needCount = count;
        if (vocabularies.size() < count * 4) {
            needCount = vocabularies.size() / 4;
        }
        if (needCount == 0) {
            return Result.fail("获取词汇练习失败");
        }
        List<VocabularyVO> vocabularyVOList = IntStream.range(0, needCount)
                .mapToObj(i -> {
                    Vocabulary vocabulary = vocabularies.get(0);
                    vocabularies.remove(0);
                    VocabularyVO vocabularyVO = new VocabularyVO();
                    vocabularyVO.setVocabularyId(vocabulary.getVocabularyId());
                    vocabularyVO.setWord(vocabulary.getWord());
                    // 生成选项
                    List<String> selections = IntStream.range(0, 3)
                            .mapToObj(j -> vocabularies.get(j).getMeaning())
                            .collect(Collectors.toList());
                    selections.add(vocabulary.getMeaning());
                    // 打乱选项顺序
                    Collections.shuffle(selections);

                    vocabularyVO.setSelections(selections);
                    return vocabularyVO;
                }).collect(Collectors.toList());
        return Result.success("获取词汇练习成功", vocabularyVOList);
    }

    @Override
    public Result submit(List<VocabularyPracticeDTO> vocabularyPracticeList) {
        List<Integer> Ids = vocabularyPracticeList.stream()
                .map(VocabularyPracticeDTO::getVocabularyId)
                .collect(Collectors.toList());
        List<Vocabulary> vocabularies = baseMapper.selectBatchIds(Ids);
        // 转换成 HashMap
        Map<Integer, Vocabulary> vocabularyMap = vocabularies.stream()
                .collect(Collectors.toMap(Vocabulary::getVocabularyId, v -> v));
        int userId = StpUtil.getLoginIdAsInt();
        // 遍历词汇练习列表，检查答案是否正确
        List<VocabularyRecord> records = vocabularyPracticeList.stream()
                .map(vocabularyPracticeDTO -> {
                    int vocabularyId = vocabularyPracticeDTO.getVocabularyId();
                    String answer = vocabularyPracticeDTO.getMeaning();
                    Vocabulary vocabulary = vocabularyMap.get(vocabularyId);
                    if (vocabulary == null) {
                        return null;
                    }
                    VocabularyRecord record = new VocabularyRecord();
                    record.setVocabularyId(vocabularyId);
                    record.setUserId(userId);
                    record.setIsCorrect(vocabulary.getMeaning().equals(answer) ? 1 : 0);
                    return record;
                })
                // 过滤掉 null 值
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        logger.info("用户{}提交了{}道题目: {}", userId, records.size(), records);
        if (records.isEmpty()) {
            return Result.fail("提交数据为空或有误");
        }
        boolean isSuccess = vocabularyRecordService.saveBatch(records);
        return isSuccess ? Result.success("提交成功") : Result.fail("提交失败");
    }

    @Override
    public Result getRecords(Page<VocabularyRecordsVO> pageDto) {
        int userId = StpUtil.getLoginIdAsInt();
        // 获取用户的词汇学习记录
        Page<VocabularyRecordsVO> records = vocabularyRecordMapper.selectByUserIdAndPage(pageDto, userId);
        if (records != null) {
            return Result.success("获取词汇学习记录成功", PageResult.of(records));
        }
        return Result.fail("获取词汇学习记录失败");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result add(List<AddVocabularyDTO> addVocabularyDTOList) {
        List<Vocabulary> vocabularyList = addVocabularyDTOList.stream()
                .map(addVocabularyDTO -> {
                    Vocabulary vocabulary = new Vocabulary();
                    vocabulary.setWord(addVocabularyDTO.getWord());
                    vocabulary.setMeaning(addVocabularyDTO.getMeaning());
                    return vocabulary;
                }).collect(Collectors.toList());

        int inserted = baseMapper.insertBatch(vocabularyList);
        if (inserted == vocabularyList.size()) {
            return Result.success("添加词汇成功");
        }
        return Result.fail("添加词汇失败");
    }
}




