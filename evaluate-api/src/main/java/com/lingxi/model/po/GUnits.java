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
 * 单元表
 * </p>
 *
 * @author tan
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("g_units")
@NotNull(message = "不能为空")
public class GUnits implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @NotNull(message = "id不为空")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 单元名称
     */
    @NotNull(message = "单元名称不为空")
    private String uName;

    /**
     * 场地id
     */
    @NotNull(message = "场地id不为空")
    private Integer playgroundId;

    /**
     * 项目数量
     */
    @NotNull(message = "项目数量不为空")
    private Integer projectCount;


}