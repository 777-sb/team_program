package com.code.cetboot.dto;

import com.code.cetboot.validation.AddListeningDTOValidation;
import com.code.cetboot.validation.AddReadingDTOValidation;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author : IMG
 * @create : 2025/5/16
 */
@Data
public class AddExerciseDTO {
    /**
     * 题干
     */
    @NotNull(
            message = "题干不能为空",
            groups = {
                    AddListeningDTOValidation.class,
                    AddReadingDTOValidation.class
            }
    )
    private String content;

    /**
     * 正确答案选项id, 为此题内选项的序号
     */
    @NotNull(
            message = "正确答案选项id不能为空",
            groups = {
                    AddListeningDTOValidation.class,
                    AddReadingDTOValidation.class
            }
    )
    private Integer answerSelectionId;

    /**
     * 选项
     */
    @NotNull(
            message = "选项不能为空",
            groups = {
                    AddListeningDTOValidation.class,
                    AddReadingDTOValidation.class
            }
    )
    @NotEmpty(
            message = "选项不能为空",
            groups = {
                    AddListeningDTOValidation.class,
                    AddReadingDTOValidation.class
            }
    )
    private List<String> selections;
}
