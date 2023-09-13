package com.lingxi.filters;

import com.alibaba.druid.util.StringUtils;
import com.lingxi.consts.MyConstants;
import com.lingxi.enums.AppHttpCodeEnum;
import com.lingxi.exception.CustomException;
import com.lingxi.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 1.获取token,如果token不存在，放行
        String authorizationHeader = request.getHeader("Authorization");
        if (StringUtils.isEmpty(authorizationHeader)) {
            filterChain.doFilter(request, response);
            return;
        }
        // 2.解析token
        if (authorizationHeader.startsWith(MyConstants.BEARER_PREFIX)) {
            String userId;
            String GUmpiresId;
            String type;
            try {
                String token = authorizationHeader.substring(7);
                Claims claims = JwtUtil.parseJWT(token);
                String subject = claims.getSubject();
                String[] strings = subject.split(",");
                type = strings[0];
                if (type.equals(MyConstants.LOGIN_USER)) {
                    userId = strings[1];
                    getUserDetailsFromRedis(MyConstants.LOGIN_USER, userId);
                } else if (type.equals(MyConstants.LOGING_GUMPIRES)) {
                    GUmpiresId = strings[1];
                    getUserDetailsFromRedis(MyConstants.LOGING_GUMPIRES, GUmpiresId);
                }
            } catch (Exception e) {
                throw new CustomException(AppHttpCodeEnum.TOKEN_EXPIRE);
            }
        }
        filterChain.doFilter(request, response);
    }

    /**
     * 获取redis中的用户信息
     *
     * @param type
     * @param id
     */
    private void getUserDetailsFromRedis(String type, String id) {
        String key = "login_" + type + ":" + id;
        Object userDetails = redisTemplate.opsForValue().get(key);
        if (userDetails == null) {
            throw new CustomException(AppHttpCodeEnum.NO_LOGIN);
        }
        saveSecurity(userDetails);
    }

    /**
     * 存储用户信息到 SecurityContextHolder
     *
     * @param userDetails
     */
    private void saveSecurity(Object userDetails) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, null);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }
}
