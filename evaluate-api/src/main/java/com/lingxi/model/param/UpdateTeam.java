package com.lingxi.model.param;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateTeam {

    /**
     * id
     */
    @NotNull(message = "id不为空")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;


    /**
     * 名称
     */
    @NotNull(message = "名称不为空")
    private String name;


    /**
     * 运动员
     */
    @NotNull(message = "运动员不为空")
    private String members;


}
