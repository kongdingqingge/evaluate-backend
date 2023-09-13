package com.lingxi.model.param;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateMatchParm {

    /**
     * 赛程 id
     */
    @NotNull(message = "赛程id不能为空")
    private Integer id;
    

    /**
     * 赛程名称
     */
    @NotNull(message = "赛程名称不能为空")
    private String mName;

    /**
     * 赛程状态;0等待中,1进行中,2已结束
     */
    @NotNull(message = "赛程状态不能为空")
    private Integer mStatus;

    /**
     * 场地数量
     */
    @NotNull(message = "赛程场地不能为空")
    private Integer playgroundCount;


}
