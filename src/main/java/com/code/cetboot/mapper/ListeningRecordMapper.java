package com.code.cetboot.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.code.cetboot.entity.ListeningRecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.code.cetboot.vo.listening.ListeningRecordsVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
* @author 19735
* @description 针对表【listening_record】的数据库操作Mapper
* @createDate 2025-04-26 01:53:46
* @Entity com.code.cetboot.entity.ListeningRecord
*/
@Mapper
public interface ListeningRecordMapper extends BaseMapper<ListeningRecord> {

    @Select("SELECT lr.*,lp.listening_practice_id," +
            " lp.title, lp.exercise_count " +
            "FROM listening_record lr " +
            "LEFT JOIN listening_practice lp ON lr.listening_practice_id = lp.listening_practice_id " +
            "WHERE lr.user_id = #{userId} " +
            "ORDER BY lr.record_time DESC")
    Page<ListeningRecordsVO> selectListeningRecordByUserId(Page<ListeningRecordsVO> page,@Param("userId") Integer userId);
}




