package com.code.cetboot.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.code.cetboot.bean.PageResult;
import com.code.cetboot.bean.Result;
import com.code.cetboot.constant.RecordType;
import com.code.cetboot.dto.AddExerciseDTO;
import com.code.cetboot.dto.AddListeningDTO;
import com.code.cetboot.dto.ExerciseDTO;
import com.code.cetboot.dto.ListeningPracticeDTO;
import com.code.cetboot.entity.*;
import com.code.cetboot.exception.ServiceException;
import com.code.cetboot.mapper.ExerciseMapper;
import com.code.cetboot.mapper.ListeningRecordMapper;
import com.code.cetboot.service.ExerciseRecordService;
import com.code.cetboot.service.ExerciseService;
import com.code.cetboot.service.ListeningPracticeService;
import com.code.cetboot.mapper.ListeningPracticeMapper;
import com.code.cetboot.service.ListeningToExerciseService;
import com.code.cetboot.vo.ExerciseVO;
import com.code.cetboot.vo.listening.ListeningPracticeVO;
import com.code.cetboot.vo.listening.ListeningRecordVO;
import com.code.cetboot.vo.listening.ListeningRecordsVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;


/**
* @author 19735
* @description 针对表【listening_practice】的数据库操作Service实现
* @createDate 2025-04-26 01:53:46
*/
@Service
public class ListeningPracticeServiceImpl extends ServiceImpl<ListeningPracticeMapper, ListeningPractice>
    implements ListeningPracticeService{

    @Resource
    private ExerciseMapper exerciseMapper;

    @Resource
    private ListeningRecordMapper listeningRecordMapper;

    @Resource
    private ExerciseRecordService exerciseRecordService;

    @Resource
    private ExerciseService exerciseService;

    @Resource
    private ListeningToExerciseService listeningToExerciseService;

    @Override
    public Result getPractices(Page<ListeningPractice> pageDto, String keyword) {
        Page<ListeningPractice> page = baseMapper.selectListeningPracticeByKeyword(pageDto, keyword);
        return Result.success("获取听力练习题成功", PageResult.of(page));
    }

    @Override
    public Result getPractice(Integer listeningPracticeId) {
        // 获取听力练习题
        ListeningPracticeVO listeningPracticeVO = getListeningPracticeVO(false, listeningPracticeId);
        if (listeningPracticeVO == null) {
            return Result.fail("获取听力练习题失败，ID不存在");
        }
        return Result.success("获取听力练习题成功", listeningPracticeVO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result submit(ListeningPracticeDTO listeningPracticeDTO) {
        Integer practiceId = listeningPracticeDTO.getListeningPracticeId();
        ListeningPractice listeningPractice = baseMapper.selectById(practiceId);
        if (listeningPractice == null) {
            return Result.fail("提交听力练习题失败，ID不存在");
        }

        // 检查题目数量
        Integer exerciseCount = listeningPractice.getExerciseCount();
        List<@Valid ExerciseDTO> exercises = listeningPracticeDTO.getExercises().stream()
                .distinct()
                .collect(Collectors.toList());
        if (exercises.size() != exerciseCount) {
            return Result.fail("提交听力练习题失败，题目数量不匹配");
        }

        List<ExerciseVO> answerList = exerciseMapper.selectExerciseByListeningPracticeId(true, practiceId);
        // 正确答案数量计数
        AtomicInteger correctCount = new AtomicInteger();
        List<ExerciseRecord> exerciseRecords = exerciseService.checkExercises(answerList, exercises, RecordType.LISTENING, correctCount);

        if (exerciseRecords.size() != exerciseCount) {
            return Result.fail("提交听力练习题失败，题目数量不匹配");
        }

        // 插入听力练习记录
        int userId = StpUtil.getLoginIdAsInt();
        ListeningRecord listeningRecord = new ListeningRecord();
        listeningRecord.setListeningPracticeId(practiceId);
        listeningRecord.setUserId(userId);
        listeningRecord.setScore(Math.round((float) correctCount.get() / exerciseCount * 100));
        listeningRecordMapper.insert(listeningRecord);

        Integer listeningRecordId = listeningRecord.getListeningRecordId();
        exerciseRecords.forEach(exerciseRecord -> {
            exerciseRecord.setRecordId(listeningRecordId);
        });
        // 批量插入
        exerciseRecordService.saveBatch(exerciseRecords);

        return Result.success("提交听力练习题成功", listeningRecord);
    }

    @Override
    public Result getRecords(Page<ListeningRecordsVO> pageDto) {
        Page<ListeningRecordsVO> page = listeningRecordMapper.selectListeningRecordByUserId(pageDto, StpUtil.getLoginIdAsInt());
        return Result.success("获取听力练习记录成功", PageResult.of(page));
    }

    @Override
    public Result getRecord(Integer listeningRecordId) {
        int userId = StpUtil.getLoginIdAsInt();
        // 获取听力练习记录
        LambdaQueryWrapper<ListeningRecord> queryWrapper = new LambdaQueryWrapper<ListeningRecord>()
                .eq(ListeningRecord::getListeningRecordId, listeningRecordId)
                .eq(ListeningRecord::getUserId, userId);
        ListeningRecord listeningRecord = listeningRecordMapper.selectOne(queryWrapper);
        if (listeningRecord == null) {
            return Result.fail("获取听力练习记录失败，听力练习记录ID不存在");
        }

        // 获取听力练习记录的题目
        Integer listeningPracticeId = listeningRecord.getListeningPracticeId();
        ListeningPracticeVO listeningPracticeVO = getListeningPracticeVO(true, listeningPracticeId);
        if (listeningPracticeVO == null) {
            return Result.fail("获取听力练习记录失败，听力练习ID不存在");
        }

        // 获取听力练习记录的答题记录
        LambdaQueryWrapper<ExerciseRecord> wrapper = new LambdaQueryWrapper<ExerciseRecord>()
                .eq(ExerciseRecord::getRecordId, listeningRecordId)
                .eq(ExerciseRecord::getUserId, userId)
                .eq(ExerciseRecord::getRecordType, RecordType.LISTENING);
        List<ExerciseRecord> exerciseRecords = exerciseRecordService.list(wrapper);
        if (exerciseRecords.isEmpty()) {
            return Result.fail("获取听力练习记录失败，答题记录不存在");
        }

        // 组合成返回对象
        ListeningRecordVO listeningRecordVO = new ListeningRecordVO(exerciseRecords, listeningPracticeVO, listeningRecord);
        return Result.success("获取听力练习记录成功", listeningRecordVO);
    }

    /**
     * 获取听力练习题目
     * @param needAnswer 是否需要答案
     * @param listeningPracticeId 听力练习题ID
     * @return ListeningPracticeVO
     */
    private ListeningPracticeVO getListeningPracticeVO(boolean needAnswer, Integer listeningPracticeId) {
        ListeningPractice listeningPractice = baseMapper.selectById(listeningPracticeId);
        if (listeningPractice == null) {
            return null;
        }
        ListeningPracticeVO listeningPracticeVO = ListeningPracticeVO.from(listeningPractice);
        List<ExerciseVO> exerciseVOS = exerciseMapper.selectExerciseByListeningPracticeId(needAnswer, listeningPracticeId);
        listeningPracticeVO.setExercises(exerciseVOS);
        return listeningPracticeVO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result add(AddListeningDTO addListeningDTO) {
        // 检查练习题的答案 id 是否合法
        List<@Valid AddExerciseDTO> exercises = addListeningDTO.getExercises();
        exercises.forEach(exercise -> {
            Integer answerSelectionId = exercise.getAnswerSelectionId();
            if (answerSelectionId == null || answerSelectionId >= exercise.getSelections().size() || answerSelectionId < 0) {
                throw new ServiceException("添加练习题失败，正确答案选项id不合法");
            }
        });

        // 设置练习题内容
        ListeningPractice listeningPractice = new ListeningPractice();
        listeningPractice.setTitle(addListeningDTO.getTitle());
        listeningPractice.setAudio(addListeningDTO.getAudio());
        listeningPractice.setExerciseCount(exercises.size());
        boolean isSaved = this.save(listeningPractice);
        if (!isSaved) {
            throw new ServiceException("保存练习题失败");
        }

        // 插入练习题
        Integer listeningPracticeId = listeningPractice.getListeningPracticeId();
        // 保存中间表项
        List<ListeningToExercise> lteList = exercises.stream()
                .map(addExerciseDTO -> {
                    Exercise exercise = new Exercise();
                    exercise.setContent(addExerciseDTO.getContent());
                    exercise.setAnswerSelectionId(addExerciseDTO.getAnswerSelectionId());
                    exerciseService.addExercise(exercise, addExerciseDTO.getSelections());
                    ListeningToExercise lte = new ListeningToExercise();
                    lte.setExerciseId(exercise.getExerciseId());
                    lte.setListeningPracticeId(listeningPracticeId);
                    return lte;
                })
                .collect(Collectors.toList());
        // 插入中间表
        isSaved = listeningToExerciseService.saveBatch(lteList);
        if (!isSaved) {
            throw new ServiceException("保存练习失败");
        }

        return Result.success("添加听力练习成功", listeningPractice);
    }
}




