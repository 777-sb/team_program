package com.code.cetboot.dto;

import com.code.cetboot.validation.AddListeningDTOValidation;
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
public class AddListeningDTO {

    /**
     * 标题
     */
    @NotNull(
            message = "标题不能为空",
            groups = {
                    AddListeningDTOValidation.class
            }
    )
    private String title;

    /**
     * 音频
     */
    @NotNull(
            message = "音频不能为空",
            groups = {
                    AddListeningDTOValidation.class
            }
    )
    private String audio;

    /**
     * 练习题列表
     */
    @NotNull(
            message = "练习题不能为空",
            groups = {
                    AddListeningDTOValidation.class
            }
    )
    @NotEmpty(
            message = "练习题不能为空",
            groups = {
                    AddListeningDTOValidation.class
            }
    )
    private List<@Valid AddExerciseDTO> exercises;
}
