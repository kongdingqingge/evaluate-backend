package com.lingxi.service.impl;


import cn.hutool.core.util.ObjectUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lingxi.consts.MyConstants;
import com.lingxi.convert.IGenerator;
import com.lingxi.enums.AppHttpCodeEnum;
import com.lingxi.exception.CustomException;
import com.lingxi.mapper.GUserMapper;
import com.lingxi.model.dto.AuthParamsDto;
import com.lingxi.model.dto.LoginReturn;
import com.lingxi.model.po.GUser;
import com.lingxi.model.vo.UserVo;
import com.lingxi.service.AuthService;
import com.lingxi.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author tan
 */
@Slf4j
@Service("user_authservice")
public class UserAuthServiceImpl implements AuthService {

    @Autowired
    private GUserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private IGenerator iGenerator;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;


    @Override
    public LoginReturn execute(AuthParamsDto authParamsDto) {

        /**
         * 1.根据账号从数据库查询
         */
        GUser gUser = userMapper.getOneByUsername(authParamsDto.getUsername());
        if (ObjectUtil.isNull(gUser)) {
            throw new CustomException(AppHttpCodeEnum.USER_DATA_NOT_EXIST);
        }

        /**
         * 2.验证用户信息
         */
        String InputPassword = authParamsDto.getPassword();
        String password = gUser.getPassword();
        boolean matches = passwordEncoder.matches(InputPassword, password);
        if (!matches) {
            throw new CustomException(AppHttpCodeEnum.LOGIN_ACCOUNT_ERROR);
        }

        /**
         * 2.认证成功生成token
         */
        String userId = gUser.getId().toString();
        String jwt = JwtUtil.createJWT(MyConstants.LOGIN_USER + "," + userId);
        String token = MyConstants.BEARER_PREFIX + jwt;

        /**
         * 3.将用户信息存入redis
         */
        HashMap<String, Object> userMap = new HashMap<>();
        ArrayList<String> strings = new ArrayList<>();
        strings.add("admin");
        userMap.put("permission", strings);
        userMap.put("userId", userId);
        userMap.put("token", token);

        // 将对象转换为 JSON 字符串
        ObjectMapper objectMapper = new ObjectMapper();
        String json = null;
        try {
            json = objectMapper.writeValueAsString(userMap);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        // 存入 Redis
        redisTemplate.opsForValue().set("login_user:" + userId, json, Duration.ofHours(4L));

        /**
         * 4.返回
         */
        UserVo userVo = iGenerator.toUserVo(gUser);
        LoginReturn loginReturn = new LoginReturn(authParamsDto.getSysteamType(), userVo, userMap);

        return loginReturn;
    }
}