package com.code.cetboot.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckRole;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.code.cetboot.bean.Result;
import com.code.cetboot.constant.Role;
import com.code.cetboot.dto.AddListeningDTO;
import com.code.cetboot.dto.ListeningPracticeDTO;
import com.code.cetboot.entity.ListeningPractice;
import com.code.cetboot.service.ListeningPracticeService;
import com.code.cetboot.validation.AddListeningDTOValidation;
import com.code.cetboot.validation.ListeningPracticeValidation;
import com.code.cetboot.vo.listening.ListeningRecordsVO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author : IMG
 * @create : 2025/4/27
 */
@RestController
@RequestMapping("/listening")
public class ListeningController {

    @Resource
    private ListeningPracticeService listeningService;

    @GetMapping("/getPractices")
    public Result getPractices(
            Page<ListeningPractice> pageDto,
            @RequestParam String keyword
    ) {
        return listeningService.getPractices(pageDto, keyword);
    }

    @GetMapping("/getPractice")
    public Result getPractice(
            @RequestParam Integer listeningPracticeId
    ) {
        return listeningService.getPractice(listeningPracticeId);
    }

    @PostMapping("/submit")
    @SaCheckLogin
    public Result submit(
            @Validated(ListeningPracticeValidation.Submit.class)
            @RequestBody ListeningPracticeDTO listeningPracticeDTO
    ) {
        return listeningService.submit(listeningPracticeDTO);
    }

    @GetMapping("/getRecords")
    @SaCheckLogin
    public Result getRecords(
            Page<ListeningRecordsVO> pageDto
    ) {
        return listeningService.getRecords(pageDto);
    }

    @GetMapping("/getRecord")
    @SaCheckLogin
    public Result getRecord(
            @RequestParam Integer listeningRecordId
    ) {
        return listeningService.getRecord(listeningRecordId);
    }

    @PostMapping("/add")
    @SaCheckLogin
    @SaCheckRole({Role.ADMIN_STRING})
    public Result add(
            @Validated(AddListeningDTOValidation.class)
            @RequestBody AddListeningDTO addListeningDTO
    ) {
        return listeningService.add(addListeningDTO);
    }
}
