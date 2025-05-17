package com.code.cetboot.dto;

import com.code.cetboot.validation.AddVocabularyDTOValidation;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author : IMG
 * @create : 2025/5/16
 */
@Data
public class AddVocabularyDTO {

    /**
     * 单词
     */
    @NotNull(
            message = "单词不能为空",
            groups = {
                    AddVocabularyDTOValidation.class
            }
    )
    private String word;

    /**
     * 单词的中文意思
     */
    @NotNull(
            message = "单词的中文意思不能为空",
            groups = {
                    AddVocabularyDTOValidation.class
            }
    )
    private String meaning;
}
