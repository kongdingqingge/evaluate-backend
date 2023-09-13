package com.lingxi.securityExceptionHandler;

import com.alibaba.fastjson.JSON;
import com.lingxi.result.ResponseResult;
import com.lingxi.utils.WebUtils;
import org.mybatis.logging.Logger;
import org.mybatis.logging.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationEntryPointImpl.class);

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        ResponseResult<Object> responceResult;
        if (authException instanceof BadCredentialsException) {
            // 处理用户名或密码错误的情况
            responceResult = ResponseResult.errorResult(400, "用户名或密码错误");
        } else if (authException instanceof InsufficientAuthenticationException) {
            // 处理其他认证异常，例如token过期等情况
            responceResult = ResponseResult.errorResult(803, "认证失败，请重新登录");
        } else {
            // 处理其他异常情况
            responceResult = ResponseResult.errorResult(500, "认证失败，未知错误");
        }
        String json = JSON.toJSONString(responceResult);
        WebUtils.renderString(response, json);
    }
}
