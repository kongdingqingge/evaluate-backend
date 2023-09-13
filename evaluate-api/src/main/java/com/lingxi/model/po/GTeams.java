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
 * 队伍表
 * </p>
 *
 * @author tan
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("g_teams")
@NotNull(message = "不能为空")
public class GTeams implements Serializable {

    private static final long serialVersionUID = 1L;

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
    private Integer projectId;

    /**
     * 名称
     */
    @NotNull(message = "名称不为空")
    private String name;

    /**
     * 队伍编号
     */
    private String number;

    /**
     * 运动员
     */
    @NotNull(message = "运动员不为空")
    private String members;


    public GTeams(String name, String members) {
        this.name = name;
        this.members = members;
    }
}