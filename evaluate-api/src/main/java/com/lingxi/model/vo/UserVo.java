package com.lingxi.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserVo {


    /**
     * id
     */
    private Integer uid;

    /**
     * 账号
     */
    private String username;

    /**
     * 密码
     */
    private String password;
}
