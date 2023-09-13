package com.lingxi.model.param;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateTeamProject {

    /**
     * id
     */
    @NotNull(message = "id不为空")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 项目id
     */
    @NotNull(message = "项目id不为空")
    private Integer pId;

    /**
     * 队伍用时
     */
    @NotNull(message = "队伍用时")
    private String time;

    /**
     * 0:等待中,1:评分中,2:评分完成,3:已发布成绩,4:弃权,5:不予评分
     */
    @NotNull(message = "状态不为空")
    private Integer status;

    /**
     * 最终得分
     */
    @NotNull(message = "最终得分")
    private double totalScore;

}
