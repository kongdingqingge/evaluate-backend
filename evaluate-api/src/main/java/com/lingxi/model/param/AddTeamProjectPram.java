package com.lingxi.model.param;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddTeamProjectPram {


    /**
     * 队伍id
     */
    @NotNull(message = "队伍id不为空")
    private Integer teamId;


    /**
     * 队伍名称
     */
    @NotNull(message = "队伍名称不为空")
    private String name;

    /**
     * 项目名称
     */
    @NotNull(message = "项目名称不为空")
    private String projectName;


}
