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
 * 用户表
 * </p>
 *
 * @author tan
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("g_user")
public class GUser implements Serializable {

    private static final long serialVersionUID = 1L;

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
    private String username;

    /**
     * 密码
     */
    @NotNull(message = "密码不为空")
    private String password;


}
