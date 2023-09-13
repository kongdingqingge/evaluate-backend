package com.lingxi.model.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 队伍项目表
 * </p>
 *
 * @author tan
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("g_team_project")
public class GTeamProject implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @NotNull(message = "id不为空")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 队伍id
     */
    @NotNull(message = "队伍id不为空")
    private Integer teamId;

    /**
     * 项目id
     */
    @NotNull(message = "项目id不为空")
    private Integer pId;

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

    /**
     * 队伍用时
     */
    private String time;

    /**
     * 主裁判扣分
     */
    private BigDecimal deTeamProjectScore;

    /**
     * 队伍项目得分
     */
    private BigDecimal addTeamProjectScore;

    /**
     * 0:等待中,1:评分中,2:评分完成,3:已发布成绩,4:弃权,5:不予评分
     */
    private Integer status;

    /**
     * 主裁判提示消息1:过低 ,2:过高
     */
    private Integer massage;

    /**
     * 最终得分
     */
    private double totalScore;

    /**
     * 备注
     */
    private String content;

    public GTeamProject(Integer teamId, Integer pId, String name, String projectName, String content) {
        this.teamId = teamId;
        this.pId = pId;
        this.name = name;
        this.projectName = projectName;
        this.content = content;
    }
}
