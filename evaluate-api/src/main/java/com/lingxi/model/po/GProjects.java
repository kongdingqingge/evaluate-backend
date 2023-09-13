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
 * 项目表
 * </p>
 *
 * @author tan
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("g_projects")
public class GProjects implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * id
     */
    @NotNull(message = "id不为空")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 项目名
     */
    @NotNull(message = "项目名不为空")
    private String pName;

    /**
     * 单元id
     */

    @NotNull(message = "单元id不为空")
    private Integer unitId;

    /**
     * 项目状态;0未开始,1进行中,2已结束
     */
    @NotNull(message = "项目状态不为空")
    private Integer pStatus;


}
