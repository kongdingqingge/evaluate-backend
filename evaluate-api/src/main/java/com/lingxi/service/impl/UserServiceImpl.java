package com.lingxi.service.impl;

import com.alibaba.fastjson.JSON;
import com.lingxi.consts.MyConstants;
import com.lingxi.enums.AppHttpCodeEnum;
import com.lingxi.exception.CustomException;
import com.lingxi.model.dto.AuthParamsDto;
import com.lingxi.model.dto.LoginReturn;
import com.lingxi.model.po.GUmpires;
import com.lingxi.model.vo.UserVo;
import com.lingxi.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * 自定义 UserDetailsService
 */
@Slf4j
@Service
public class UserServiceImpl implements UserDetailsService {

    @Autowired
    ApplicationContext applicationContext;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        try {
            // 将认证参数转为统一参数
            AuthParamsDto authParamsDto = JSON.parseObject(s, AuthParamsDto.class);

            // 根据认证类型从容器取出对应的 bean
            String type = authParamsDto.getSysteamType();
            AuthService authService = applicationContext.getBean(type + "_authservice", AuthService.class);

            // 执行 bean 的方法，返回 LoginReturn 对象
            LoginReturn loginReturn = authService.execute(authParamsDto);
            loginReturn.setMessageMap(null);

            // 根据登录类型返回 UserDetails
            return getUserDetails(loginReturn);
        } catch (Exception e) {
            log.error("Error occurred during user authentication: {}", e.getMessage());
            throw new CustomException(AppHttpCodeEnum.AUTH_DATA_ERROR);
        }
    }

    /**
     * 根据登录类型返回 UserDetails
     *
     * @param loginReturn
     * @return
     */
    private UserDetails getUserDetails(LoginReturn loginReturn) {
        String systemType = loginReturn.getSysteamType();
        UserVo userVo;
        if (systemType.equals(MyConstants.LOGIN_USER)) {
            userVo = loginReturn.getUserVo();
            userVo.setPassword("");
            return getUserDetailsFromUserVo(userVo);
        } else if (systemType.equals(MyConstants.LOGING_GUMPIRES)) {
            GUmpires GUmpires = loginReturn.getGUmpires();
            return getUserDetailsFromGUmpires(GUmpires);
        } else {
            throw new CustomException(AppHttpCodeEnum.AUTH_DATA_ERROR);
        }
    }

    private UserDetails getUserDetailsFromUserVo(UserVo userVo) {
        String userJsonString = JSON.toJSONString(userVo);
        return User.withUsername(userJsonString)
                .password("")
                .authorities("")
                .build();
    }

    private UserDetails getUserDetailsFromGUmpires(GUmpires GUmpires) {
        String GUmpiresJsonString = JSON.toJSONString(GUmpires);
        return User.withUsername(GUmpiresJsonString)
                .password("")
                .authorities("")
                .build();
    }
}
