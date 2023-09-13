package com.lingxi.model.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * <p>
 * 裁判表
 * </p>
 *
 * @author tan
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("g_umpires")
public class GUmpires {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @NotNull(message = "id不为空")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 场地id
     */
    @NotNull(message = "场地id不为空")
    private Integer playgroundId;

    /**
     * 单元id
     */
    @NotNull(message = "单元id不为空")
    private Integer unitId;

    /**
     * 项目id
     */
    @NotNull(message = "项目id不为空")
    private Integer projectId;

    /**
     * 账号
     */
    @NotNull(message = "账号不为空")
    private String account;

    /**
     * 姓名
     */
    @NotNull(message = "姓名不为空")
    private String name;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 类型
     */
    @NotNull(message = "类型不为空")
    private Integer type;

    /**
     * 机位号
     */
    @NotNull(message = "机位号不为空")
    private Integer location;


}
