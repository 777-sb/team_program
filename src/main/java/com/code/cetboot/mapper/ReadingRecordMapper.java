package com.code.cetboot.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.code.cetboot.entity.ReadingRecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.code.cetboot.vo.reading.ReadingRecordsVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
* @author 19735
* @description 针对表【reading_record】的数据库操作Mapper
* @createDate 2025-04-26 01:53:46
* @Entity com.code.cetboot.entity.ReadingRecord
*/
@Mapper
public interface ReadingRecordMapper extends BaseMapper<ReadingRecord> {

    @Select("SELECT rr.*, rp.reading_practice_id, " +
            "rp.title, rp.exercise_count " +
            "FROM reading_record rr " +
            "LEFT JOIN reading_practice rp ON rr.reading_practice_id = rp.reading_practice_id " +
            "WHERE rr.user_id = #{userId} " +
            "ORDER BY rr.record_time DESC")
    Page<ReadingRecordsVO> selectReadingRecordByUserId(Page<ReadingRecordsVO> page,@Param("userId") Integer userId);
}




