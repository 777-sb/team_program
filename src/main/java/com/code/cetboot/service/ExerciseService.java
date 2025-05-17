package com.code.cetboot.service;

import com.code.cetboot.dto.ExerciseDTO;
import com.code.cetboot.entity.Exercise;
import com.baomidou.mybatisplus.extension.service.IService;
import com.code.cetboot.entity.ExerciseRecord;
import com.code.cetboot.vo.ExerciseVO;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
* @author 19735
* @description 针对表【exercise】的数据库操作Service
* @createDate 2025-04-26 01:53:30
*/
public interface ExerciseService extends IService<Exercise> {

    /**
     * 批改练习题
     * @param answerList 答案列表
     * @param exercises 练习题列表
     * @param recordType 记录类型
     * @param correctCount 正确答案数量
     * @return 练习记录列表
     */
    List<ExerciseRecord> checkExercises(List<ExerciseVO> answerList, List<ExerciseDTO> exercises, Integer recordType, AtomicInteger correctCount);

    /**
     * 添加练习题
     * @param exercise 练习题
     * @param selections 选项列表
     */
    void addExercise(Exercise exercise, List<String> selections);
}
