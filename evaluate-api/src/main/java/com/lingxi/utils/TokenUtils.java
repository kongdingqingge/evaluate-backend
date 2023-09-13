package com.lingxi.utils;

import com.lingxi.mapper.GUmpiresMapper;
import com.lingxi.model.po.GUmpires;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@Slf4j
@Component
public class TokenUtils {

    @Resource
    private GUmpiresMapper gUmpiresMapper;

    private static GUmpiresMapper staticgUmpiresMapper;

    @PostConstruct
    public void init() {
        staticgUmpiresMapper = gUmpiresMapper;
    }

    /**
     * 解析token,获取裁判数据
     *
     * @param token
     * @return com.lingxi.model.po.GUmpires
     * @author Cyou
     */
    public static GUmpires getUmpires(String token) {
        try {
            Claims claims = JwtUtil.parseJWT(token);
            String subject = claims.getSubject();
            String aud = subject.substring(subject.indexOf(',') + 1);
            Integer umpiresId = Integer.valueOf(aud);
            return staticgUmpiresMapper.getUmpires(umpiresId);
        } catch (Exception e) {
            log.error("解析token失败", e);
            return null;
        }
    }
}