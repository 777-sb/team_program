package com.code.cetboot.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckRole;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.code.cetboot.bean.Result;
import com.code.cetboot.constant.Role;
import com.code.cetboot.dto.AddReadingDTO;
import com.code.cetboot.dto.ReadingPracticeDTO;
import com.code.cetboot.entity.ReadingPractice;
import com.code.cetboot.service.ReadingPracticeService;
import com.code.cetboot.validation.AddReadingDTOValidation;
import com.code.cetboot.validation.ReadingPracticeValidation;
import com.code.cetboot.vo.reading.ReadingRecordsVO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author : IMG
 * @create : 2025/4/28
 */
@RestController
@RequestMapping("/reading")
public class ReadingController {

    @Resource
    private ReadingPracticeService readingService;

    @GetMapping("/getPractices")
    public Result getPractices(
            Page<ReadingPractice> pageDto,
            @RequestParam String keyword
    ) {
        return readingService.getPractices(pageDto, keyword);
    }

    @GetMapping("/getPractice")
    public Result getPractice(
            @RequestParam Integer readingPracticeId
    ) {
        return readingService.getPractice(readingPracticeId);
    }

    @PostMapping("/submit")
    @SaCheckLogin
    public Result submit(
            @Validated({ReadingPracticeValidation.Submit.class})
            @RequestBody ReadingPracticeDTO readingPracticeDTO
    ) {
        return readingService.submit(readingPracticeDTO);
    }

    @GetMapping("/getRecords")
    @SaCheckLogin
    public Result getRecords(
            Page<ReadingRecordsVO> pageDto
    ) {
        return readingService.getRecords(pageDto);
    }

    @GetMapping("/getRecord")
    @SaCheckLogin
    public Result getRecord(
            @RequestParam Integer readingRecordId
    ) {
        return readingService.getRecord(readingRecordId);
    }

    @PostMapping("/add")
    @SaCheckRole({Role.ADMIN_STRING})
    public Result add(
            @Validated({AddReadingDTOValidation.class})
            @RequestBody AddReadingDTO addReadingDTO
    ) {
        return readingService.add(addReadingDTO);
    }
}
