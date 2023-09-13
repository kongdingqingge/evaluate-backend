package com.lingxi.service;


import com.lingxi.model.dto.AuthParamsDto;
import com.lingxi.model.dto.LoginReturn;

/**
 * 统一认证接口
 */
public interface AuthService {

    /**
     * 认证方法
     */
    LoginReturn execute(AuthParamsDto authParamsDto);

}
