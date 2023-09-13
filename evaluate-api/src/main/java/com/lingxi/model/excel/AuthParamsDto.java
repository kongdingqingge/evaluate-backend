package com.lingxi.model.excel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 登入统一提交数据
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthParamsDto {

    /**
     * 登入系统的类型
     */
    private String SysteamType;

    /**
     * 账号
     */
    private String username;

    /**
     * 密码
     */
    private String password;


    /**
     * 裁判账号
     */
    private String account;


}
