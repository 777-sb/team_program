package com.code.cetboot.mapper;

import com.code.cetboot.entity.Vocabulary;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author 19735
* @description 针对表【vocabulary】的数据库操作Mapper
* @createDate 2025-04-26 01:53:46
* @Entity com.code.cetboot.entity.Vocabulary
*/
@Mapper
public interface VocabularyMapper extends BaseMapper<Vocabulary> {

    /**
     * 随机查询指定数量的词汇
     *
     * @param count 查询的数量
     * @return 随机查询到的词汇列表
     */
    List<Vocabulary> selectBatchRandom(@Param("count") int count);

    /**
     * 批量插入词汇, 如果词汇已存在则跳过
     *
     * @param vocabularyList 词汇列表
     * @return 插入成功的数量
     */
    int insertBatch(@Param("vocabularyList") List<Vocabulary> vocabularyList);
}




