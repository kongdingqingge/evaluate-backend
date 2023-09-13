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
 * 赛程表
 * </p>
 *
 * @author tan
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("g_matches")
public class GMatches implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    @NotNull(message = "id不为空")
    private Integer id;

    /**
     * 赛程名称
     */
    @NotNull(message = "赛程名称不为空")
    private String mName;

    /**
     * 赛程状态;0等待中,1进行中,2已结束
     */
    @NotNull(message = "赛程状态不为空")
    private Integer mStatus;

    /**
     * 场地数量
     */
    @NotNull(message = "场地数量不为空")
    private Integer playgroundCount;


}
