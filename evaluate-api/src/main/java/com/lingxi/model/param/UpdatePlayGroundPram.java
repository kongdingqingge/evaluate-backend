package com.lingxi.model.param;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePlayGroundPram {


    /**
     * id
     */

    @NotNull(message = "赛程id不能为空")
    private Integer id;

    /**
     * 场地名称
     */
    @NotNull(message = "赛程id不能为空")
    private String playgroundName;

    /**
     * 赛程id
     */
    @NotNull(message = "赛程id不能为空")
    private Integer matchId;

    /**
     * 单元数量
     */
    @NotNull(message = "赛程id不能为空")
    private Integer unitCount;


}
