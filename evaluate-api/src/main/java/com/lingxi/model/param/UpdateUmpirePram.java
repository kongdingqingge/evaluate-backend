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
public class UpdateUmpirePram {

    /**
     * id
     */
    @NotNull(message = "id不为空")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;


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
     * 类型
     */
    @NotNull(message = "类型不为空")
    private Integer type;


}
