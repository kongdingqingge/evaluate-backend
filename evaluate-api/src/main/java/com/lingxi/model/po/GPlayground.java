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
 * 场地
 * </p>
 *
 * @author tan
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("g_playground")
public class GPlayground implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @NotNull(message = "id不为空")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 场地名称
     */
    @NotNull(message = "场地名称不为空")
    private String playgroundName;

    /**
     * 赛程id
     */
    @NotNull(message = "赛程id不为空")
    private Integer matchId;

    /**
     * 单元数量
     */
    @NotNull(message = "单元数量不为空")
    private Integer unitCount;

}
