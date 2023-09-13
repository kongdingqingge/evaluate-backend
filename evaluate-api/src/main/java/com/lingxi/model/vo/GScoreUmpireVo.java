package com.lingxi.model.vo;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GScoreUmpireVo {


    /**
     * id
     */
    @NotNull(message = "id不为空")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 队伍名称
     */
    @NotNull(message = "项目名称不为空")
    private String name;

    /**
     * 分数
     */
    private String score;

    /**
     * 单元名称
     */
    @NotNull(message = "单元名称不为空")
    private String uName;

    /**
     * 项目名
     */
    @NotNull(message = "项目名不为空")
    private String pName;


}