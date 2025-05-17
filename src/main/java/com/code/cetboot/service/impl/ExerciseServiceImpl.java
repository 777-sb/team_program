package com.code.cetboot.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.code.cetboot.dto.ExerciseDTO;
import com.code.cetboot.entity.Exercise;
import com.code.cetboot.entity.ExerciseRecord;
import com.code.cetboot.entity.ExerciseSelection;
import com.code.cetboot.exception.ServiceException;
import com.code.cetboot.service.ExerciseSelectionService;
import com.code.cetboot.service.ExerciseService;
import com.code.cetboot.mapper.ExerciseMapper;
import com.code.cetboot.vo.ExerciseVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
* @author 19735
* @description 针对表【exercise】的数据库操作Service实现
* @createDate 2025-04-26 01:53:30
*/
@Service
public class ExerciseServiceImpl extends ServiceImpl<ExerciseMapper, Exercise>
    implements ExerciseService{

    @Resource
    private ExerciseSelectionService exerciseSelectionService;

    @Override
    public List<ExerciseRecord> checkExercises(List<ExerciseVO> answerList, List<ExerciseDTO> exercises, Integer recordType, AtomicInteger correctCount) {
        // 转为 HashMap
        Map<Integer, ExerciseVO> answerMap = answerList.stream()
                .collect(Collectors.toMap(ExerciseVO::getExerciseId, v -> v));
        int userId = StpUtil.getLoginIdAsInt();
        return exercises.stream()
                .map(exerciseDTO -> {
                    Integer exerciseId = exerciseDTO.getExerciseId();
                    Integer selectionId = exerciseDTO.getSelectionId();
                    ExerciseVO exerciseVO = answerMap.get(exerciseId);
                    if (exerciseVO == null) {
                        return null;
                    }

                    // 判断答案是否正确
                    exerciseVO.getSelections().stream()
                            .filter(selection -> selection.getExerciseSelectionId().equals(selectionId))
                            .findFirst()
                            .orElseThrow(() -> new ServiceException("提交阅读练习题失败, 选项不存在"));

                    // 创建练习记录
                    ExerciseRecord exerciseRecord = new ExerciseRecord();
                    exerciseRecord.setExerciseId(exerciseId);
                    exerciseRecord.setExerciseSelectionId(selectionId);
                    exerciseRecord.setUserId(userId);
                    if (exerciseVO.getAnswerSelectionId().equals(selectionId)) {
                        exerciseRecord.setIsCorrect(1);
                        correctCount.incrementAndGet();
                    } else {
                        exerciseRecord.setIsCorrect(0);
                    }
                    exerciseRecord.setRecordType(recordType);
                    return exerciseRecord;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addExercise(Exercise exercise, List<String> selections) {
        Integer answerSelectionId = exercise.getAnswerSelectionId();
        if (answerSelectionId == null || answerSelectionId >= selections.size() || answerSelectionId < 0) {
            throw new ServiceException("添加练习题失败，正确答案选项id不合法");
        }

        boolean isSaved = this.save(exercise);
        if (!isSaved) {
            throw new ServiceException("添加练习题失败");
        }
        Integer exerciseId = exercise.getExerciseId();

        List<ExerciseSelection> selectionList = selections.stream()
                .map(selection -> {
                    ExerciseSelection exerciseSelection = new ExerciseSelection();
                    exerciseSelection.setExerciseId(exerciseId);
                    exerciseSelection.setSelection(selection);
                    return exerciseSelection;
                }).collect(Collectors.toList());
        isSaved = exerciseSelectionService.saveBatch(selectionList);
        if (!isSaved) {
            throw new ServiceException("添加练习题失败");
        }

        // 设置正确答案
        Integer exerciseSelectionId = selectionList.get(answerSelectionId).getExerciseSelectionId();
        exercise.setAnswerSelectionId(exerciseSelectionId);
        isSaved = this.updateById(exercise);
        if (!isSaved) {
            throw new ServiceException("添加练习题失败");
        }
    }
}




