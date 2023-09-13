package com.lingxi.model.param;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddScore {

    /**
     * 裁判id
     */
    @NotNull(message = "裁判id不为空")
    private Integer umpireId;

    /**
     * 项目队伍表id
     */
    @NotNull(message = "项目队伍id不为空")
    private Integer teamProjectId;

    /**
     * 项目id
     */
    @NotNull(message = "项目id不为空")
    private Integer projectId;

    /**
     * 队伍名称
     */
    @NotNull(message = "项目名称不为空")
    private String name;

    /**
     * 分数
     */
    private Double score;


}
