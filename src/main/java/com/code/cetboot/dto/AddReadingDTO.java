package com.code.cetboot.dto;

import com.code.cetboot.validation.AddReadingDTOValidation;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author : IMG
 * @create : 2025/5/16
 */
@Data
public class AddReadingDTO {
    /**
     * 标题
     */
    @NotNull(
            message = "标题不能为空",
            groups = {
                    AddReadingDTOValidation.class
            }
    )
    private String title;

    /**
     * 文章
     */
    @NotNull(
            message = "文章不能为空",
            groups = {
                    AddReadingDTOValidation.class
            }
    )
    private String content;

    /**
     * 练习题列表
     */
    @NotNull(
            message = "练习题不能为空",
            groups = {
                    AddReadingDTOValidation.class
            }
    )
    @NotEmpty(
            message = "练习题不能为空",
            groups = {
                    AddReadingDTOValidation.class
            }
    )
    private List<@Valid AddExerciseDTO> exercises;
}
