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
public class UpdateTeamProjectPram {

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    

    /**
     * 项目名称
     */
    @NotNull(message = "项目名称不为空")
    private String projectName;

}
