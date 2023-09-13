package com.lingxi.utils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;


/**
 * 自定义获取权限的方法
 * 定义自己的权限校验方法，在@PreAuthorize注解中使用
 */
@Component("TAN")
public class AuthorizeUtil {

    public boolean hasAuthority(String authority) {
        //获取当前用户权限
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        JsonArray permissionArray = null;
        if (principal instanceof String) {
            String jsonString = (String) principal;
            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(jsonString, JsonObject.class);
            // 获取permission字段的集合
            permissionArray = jsonObject.getAsJsonArray("permission");
        }
        //判断权限集合中存在 authority
        return permissionArray != null && permissionArray.contains(new JsonPrimitive(authority));
    }
}

