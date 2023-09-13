package com.lingxi.utils;


import com.alibaba.fastjson.JSON;
import com.lingxi.enums.AppHttpCodeEnum;
import com.lingxi.exception.CustomException;
import com.lingxi.model.vo.UmpireRedisVo;
import com.lingxi.model.vo.UserRedisVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;

@Slf4j
public class SecurityUtil {

    public static UserRedisVo getOnlineUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.info("principal:{}", principal);
        if (principal instanceof String) {
            String jsonString = (String) principal;
            log.info("jsonString:{}", jsonString);
            UserRedisVo userRedisVo = null;
            try {
                userRedisVo = JSON.parseObject(jsonString, UserRedisVo.class);
            } catch (Exception e) {
                throw new CustomException(AppHttpCodeEnum.USER_TRSFOR_ERROR);
            }
            return userRedisVo;
        }
        return null;
    }


    public static UmpireRedisVo getOnlineGUmpires() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof String) {
            String jsonString = (String) principal;
            UmpireRedisVo GUmpiresRedisVo = null;
            try {
                GUmpiresRedisVo = JSON.parseObject(jsonString, UmpireRedisVo.class);
            } catch (Exception e) {
                throw new CustomException(AppHttpCodeEnum.GUmpires_TRSFOR_ERROR);
            }
            return GUmpiresRedisVo;
        }
        return null;
    }
}
