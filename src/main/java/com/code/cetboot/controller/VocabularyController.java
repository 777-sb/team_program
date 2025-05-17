package com.code.cetboot.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckRole;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.code.cetboot.bean.Result;
import com.code.cetboot.constant.Role;
import com.code.cetboot.dto.AddVocabularyDTO;
import com.code.cetboot.dto.VocabularyPracticeDTO;
import com.code.cetboot.service.VocabularyService;
import com.code.cetboot.validation.AddVocabularyDTOValidation;
import com.code.cetboot.validation.VocabularyPracticeValidation;
import com.code.cetboot.vo.vocabulary.VocabularyRecordsVO;
import org.slf4j.Logger;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author : IMG
 * @create : 2025/4/27
 */
@RestController
@RequestMapping("/vocabulary")
public class VocabularyController {

    @Resource
    private VocabularyService vocabularyService;

    @Resource
    private Logger logger;

    @GetMapping("/getPractices")
    @SaCheckLogin
    public Result getPractices(
            @RequestParam Integer count
    ) {
        return vocabularyService.getPractices(count);
    }

    @PostMapping("submit")
    @SaCheckLogin
    public Result submit(
            @Validated(VocabularyPracticeValidation.Submit.class)
            @RequestBody List<VocabularyPracticeDTO> vocabularyPracticeList
    ) {
        if (vocabularyPracticeList.isEmpty()) {
            return Result.fail("数据不能为空");
        }
        return vocabularyService.submit(vocabularyPracticeList);
    }

    @GetMapping("/getRecords")
    @SaCheckLogin
    public Result getRecords(
            Page<VocabularyRecordsVO> pageDto
    ) {
        return vocabularyService.getRecords(pageDto);
    }

    @PostMapping("/add")
    @SaCheckLogin
    @SaCheckRole({Role.ADMIN_STRING})
    public Result add(
            @Validated(AddVocabularyDTOValidation.class)
            @NotNull(message = "数据不能为空", groups = {AddVocabularyDTOValidation.class})
            @NotEmpty(message = "数据不能为空", groups = {AddVocabularyDTOValidation.class})
            @RequestBody List<@Valid AddVocabularyDTO> addVocabularyDTOList
    ){
        return vocabularyService.add(addVocabularyDTOList);
    }
}
