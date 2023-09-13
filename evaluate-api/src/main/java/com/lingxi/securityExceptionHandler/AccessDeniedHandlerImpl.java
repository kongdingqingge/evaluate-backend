package com.lingxi.securityExceptionHandler;

import com.alibaba.fastjson.JSON;
import com.lingxi.result.ResponseResult;
import com.lingxi.utils.WebUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        ResponseResult<Object> result = new ResponseResult<>(403, "您的权限不足");
        String json = JSON.toJSONString(result);
        WebUtils.renderString(response, json);//处理异常
    }
}
