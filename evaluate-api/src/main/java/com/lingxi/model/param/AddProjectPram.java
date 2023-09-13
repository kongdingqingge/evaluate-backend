package com.lingxi.model.param;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddProjectPram {


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
