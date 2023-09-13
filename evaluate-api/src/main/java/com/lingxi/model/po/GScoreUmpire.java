package com.lingxi.model.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * <p>
 * 裁判打分表
 * </p>
 *
 * @author tan
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("g_score_umpire")
public class GScoreUmpire implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @NotNull(message = "id不为空")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

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
    private String score;

    public GScoreUmpire(Integer umpireId, Integer teamProjectId, Integer projectId, String name, String score) {
        this.umpireId = umpireId;
        this.teamProjectId = teamProjectId;
        this.projectId = projectId;
        this.name = name;
        this.score = score;
    }
}