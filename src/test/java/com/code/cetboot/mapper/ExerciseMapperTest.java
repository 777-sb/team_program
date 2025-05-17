package com.code.cetboot.mapper;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * @author : IMG
 * @create : 2025/4/27
 */
@SpringBootTest
public class ExerciseMapperTest {

    @Resource
    private ExerciseMapper exerciseMapper;

    @Resource
    private Logger logger;

    @Test
    public void testSelectByListeningPracticeId() {
//        logger.info("{}", exerciseMapper.selectExerciseByListeningPracticeId(true, 1));
//        logger.info("{}", exerciseMapper.selectExerciseByListeningPracticeId(false, 1));
    }
}
