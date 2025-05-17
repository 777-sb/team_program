package com.code.cetboot.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.code.cetboot.bean.PageResult;
import com.code.cetboot.bean.Result;
import com.code.cetboot.constant.RecordType;
import com.code.cetboot.dto.AddExerciseDTO;
import com.code.cetboot.dto.AddReadingDTO;
import com.code.cetboot.dto.ExerciseDTO;
import com.code.cetboot.dto.ReadingPracticeDTO;
import com.code.cetboot.entity.*;
import com.code.cetboot.exception.ServiceException;
import com.code.cetboot.mapper.ExerciseMapper;
import com.code.cetboot.mapper.ReadingRecordMapper;
import com.code.cetboot.service.ExerciseRecordService;
import com.code.cetboot.service.ExerciseService;
import com.code.cetboot.service.ReadingPracticeService;
import com.code.cetboot.mapper.ReadingPracticeMapper;
import com.code.cetboot.service.ReadingToExerciseService;
import com.code.cetboot.vo.ExerciseVO;
import com.code.cetboot.vo.reading.ReadingPracticeVO;
import com.code.cetboot.vo.reading.ReadingRecordVO;
import com.code.cetboot.vo.reading.ReadingRecordsVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
* @author 19735
* @description 针对表【reading_practice】的数据库操作Service实现
* @createDate 2025-04-26 01:53:46
*/
@Service
public class ReadingPracticeServiceImpl extends ServiceImpl<ReadingPracticeMapper, ReadingPractice>
    implements ReadingPracticeService{

    @Resource
    private ExerciseMapper exerciseMapper;

    @Resource
    private ReadingRecordMapper readingRecordMapper;

    @Resource
    private ExerciseRecordService exerciseRecordService;

    @Resource
    private ExerciseService exerciseService;

    @Resource
    private ReadingToExerciseService readingToExerciseService;

    @Override
    public Result getPractices(Page<ReadingPractice> pageDto, String keyword) {
        Page<ReadingPractice> readingPracticePage = baseMapper.selectReadingPracticeByKeyword(pageDto, keyword);
        return Result.success("获取阅读练习题成功", PageResult.of(readingPracticePage));
    }

    @Override
    public Result getPractice(Integer practiceId) {
        ReadingPracticeVO readingPracticeVO = getReadingPracticeVO(false, practiceId);
        if (readingPracticeVO == null) {
            return Result.fail("获取阅读练习题失败, ID不存在");
        }
        return Result.success("获取阅读练习题成功", readingPracticeVO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result submit(ReadingPracticeDTO readingPracticeDTO) {
        Integer practiceId = readingPracticeDTO.getReadingPracticeId();
        ReadingPractice readingPractice = baseMapper.selectById(practiceId);
        if (readingPractice == null) {
            return Result.fail("提交阅读练习题失败, ID不存在");
        }

        // 检查题目数量
        Integer exerciseCount = readingPractice.getExerciseCount();
        List<@Valid ExerciseDTO> exercises = readingPracticeDTO.getExercises().stream()
                .distinct()
                .collect(Collectors.toList());
        if (exercises.size() != exerciseCount) {
            return Result.fail("提交阅读练习题失败, 题目数量不匹配");
        }

        List<ExerciseVO> answerList = exerciseMapper.selectExerciseByReadingPracticeId(true, practiceId);
        // 正确答案数量计数
        AtomicInteger correctCount = new AtomicInteger();
        List<ExerciseRecord> exerciseRecords = exerciseService.checkExercises(answerList, exercises, RecordType.READING, correctCount);
        if (exerciseRecords.size() != exerciseCount) {
            return Result.fail("提交阅读练习题失败, 题目数量不匹配");
        }

        // 插入练习记录
        int userId = StpUtil.getLoginIdAsInt();
        ReadingRecord readingRecord = new ReadingRecord();
        readingRecord.setReadingPracticeId(practiceId);
        readingRecord.setUserId(userId);
        readingRecord.setScore(Math.round((float) correctCount.get() / exerciseCount * 100));
        readingRecordMapper.insert(readingRecord);

        Integer readingRecordId = readingRecord.getReadingRecordId();
        exerciseRecords.forEach(exerciseRecord -> {
            exerciseRecord.setRecordId(readingRecordId);
        });
        // 批量插入
        exerciseRecordService.saveBatch(exerciseRecords);
        return Result.success("提交阅读练习题成功", readingRecord);
    }

    @Override
    public Result getRecords(Page<ReadingRecordsVO> pageDto) {
        Page<ReadingRecordsVO> recordsVOPage = readingRecordMapper.selectReadingRecordByUserId(pageDto, StpUtil.getLoginIdAsInt());
        return Result.success("获取阅读记录成功", PageResult.of(recordsVOPage));
    }

    @Override
    public Result getRecord(Integer readingRecordId) {
        int userId = StpUtil.getLoginIdAsInt();
        // 获取阅读记录
        LambdaQueryWrapper<ReadingRecord> queryWrapper = new LambdaQueryWrapper<ReadingRecord>()
                .eq(ReadingRecord::getReadingRecordId, readingRecordId)
                .eq(ReadingRecord::getUserId, userId);
        ReadingRecord readingRecord = readingRecordMapper.selectOne(queryWrapper);
        if (readingRecord == null) {
            return Result.fail("获取阅读记录失败, 阅读练习记录ID不存在");
        }

        // 获取阅读练习记录的题目
        Integer readingPracticeId = readingRecord.getReadingPracticeId();
        ReadingPracticeVO readingPracticeVO = getReadingPracticeVO(true, readingPracticeId);
        if (readingPracticeVO == null) {
            return Result.fail("获取阅读记录失败, 阅读练习ID不存在");
        }

        // 获取阅读练习记录的答题记录
        LambdaQueryWrapper<ExerciseRecord> wrapper = new LambdaQueryWrapper<ExerciseRecord>()
                .eq(ExerciseRecord::getRecordId, readingRecordId)
                .eq(ExerciseRecord::getUserId, userId)
                .eq(ExerciseRecord::getRecordType, RecordType.READING);
        List<ExerciseRecord> exerciseRecords = exerciseRecordService.list(wrapper);
        if (exerciseRecords == null || exerciseRecords.isEmpty()) {
            return Result.fail("获取阅读记录失败, 答题记录不存在");
        }
        // 组合成返回对象
        ReadingRecordVO readingRecordVO = new ReadingRecordVO(exerciseRecords, readingPracticeVO, readingRecord);
        return Result.success("获取阅读记录成功", readingRecordVO);
    }

    private ReadingPracticeVO getReadingPracticeVO(boolean needAnswer, Integer readingPracticeId) {
        ReadingPractice readingPractice = baseMapper.selectById(readingPracticeId);
        if (readingPractice == null) {
            return null;
        }
        ReadingPracticeVO readingPracticeVO = ReadingPracticeVO.from(readingPractice);
        List<ExerciseVO> exerciseVOS = exerciseMapper.selectExerciseByReadingPracticeId(needAnswer, readingPracticeId);
        readingPracticeVO.setExercises(exerciseVOS);
        return readingPracticeVO;
    }

    @Override
    public Result add(AddReadingDTO addReadingDTO) {
        // 检查练习题的答案 id 是否合法
        List<@Valid AddExerciseDTO> exercises = addReadingDTO.getExercises();
        exercises.forEach(exercise -> {
            Integer answerSelectionId = exercise.getAnswerSelectionId();
            if (answerSelectionId == null || answerSelectionId >= exercise.getSelections().size() || answerSelectionId < 0) {
                throw new ServiceException("添加练习题失败，正确答案选项id不合法");
            }
        });

        // 设置练习题内容
        ReadingPractice readingPractice = new ReadingPractice();
        readingPractice.setTitle(addReadingDTO.getTitle());
        readingPractice.setContent(addReadingDTO.getContent());
        readingPractice.setExerciseCount(exercises.size());
        boolean isSaved = this.save(readingPractice);
        if (!isSaved) {
            throw new ServiceException("保存练习题失败");
        }

        // 插入练习题
        Integer readingPracticeId = readingPractice.getReadingPracticeId();
        List<ReadingToExercise> rteList = exercises.stream()
                .map(addExerciseDTO -> {
                    Exercise exercise = new Exercise();
                    exercise.setContent(addExerciseDTO.getContent());
                    exercise.setAnswerSelectionId(addExerciseDTO.getAnswerSelectionId());
                    exerciseService.addExercise(exercise, addExerciseDTO.getSelections());
                    ReadingToExercise rte = new ReadingToExercise();
                    rte.setReadingPracticeId(readingPracticeId);
                    rte.setExerciseId(exercise.getExerciseId());
                    return rte;
                }).collect(Collectors.toList());
        // 插入中间表
        isSaved = readingToExerciseService.saveBatch(rteList);
        if (!isSaved) {
            throw new ServiceException("添加练习题失败");
        }
        readingPractice.setContent(null);
        return Result.success("添加阅读练习成功", readingPractice);
    }
}




