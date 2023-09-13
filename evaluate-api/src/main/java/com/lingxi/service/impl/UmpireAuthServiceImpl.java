package com.lingxi.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lingxi.consts.MyConstants;
import com.lingxi.enums.AppHttpCodeEnum;
import com.lingxi.exception.CustomException;
import com.lingxi.mapper.GUmpiresMapper;
import com.lingxi.model.dto.AuthParamsDto;
import com.lingxi.model.dto.LoginReturn;
import com.lingxi.model.po.GUmpires;
import com.lingxi.service.AuthService;
import com.lingxi.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;

@Service("umpire_auth-service")
public class UmpireAuthServiceImpl implements AuthService {

    @Autowired
    private GUmpiresMapper gUmpiresMapper;


    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    private static HashMap<String, Object> getStringObjectHashMap(String jwt, GUmpires umpires, String GUmpiresId, Integer mainId) {
        String token = "Bearer " + jwt;
        /**
         * 3.将用户信息存入redis
         */
        HashMap<String, Object> GUmpiresMap = new HashMap<>();
        ArrayList<String> strings = new ArrayList<>();
        if (umpires.getType() == 1) {
            strings.add("common");
        } else if (umpires.getType() == 2) {
            strings.add("primary");
        }
        if (umpires.getType() == 1) {

            GUmpiresMap.put("location", umpires.getLocation());
            GUmpiresMap.put("mainId", mainId);
        }
        GUmpiresMap.put("permission", strings);
        GUmpiresMap.put("GUmpiresId", GUmpiresId);
        GUmpiresMap.put("token", token);
        GUmpiresMap.put("GUmpires_type", umpires.getType());
        GUmpiresMap.put("GUmpires_playGround", umpires.getPlaygroundId());
        GUmpiresMap.put("avatar", umpires.getAvatar());
        return GUmpiresMap;
    }

    @Override
    public LoginReturn execute(AuthParamsDto authParamsDto) {
        /**
         * 1.根据账号查询,验证裁判
         */
        GUmpires umpires = gUmpiresMapper.getUmpiresByAccount(authParamsDto.getAccount());

        if (ObjectUtil.isNull(umpires)) {
            throw new CustomException(AppHttpCodeEnum.EMPIRES_DATA_NOT_EXIST);
        }
        umpires.setLocation(authParamsDto.getLocation());
        int i = gUmpiresMapper.updateById(umpires);
        if (i < 0) {
            throw new CustomException(AppHttpCodeEnum.EMPIRES_LOCATION_ERROR);
        }
        /**
         * 2.认证成功生成token
         */
        String GUmpiresId = umpires.getId().toString();
        String jwt = JwtUtil.createJWT(MyConstants.LOGING_GUMPIRES + "," + GUmpiresId);
        //场地id查询主裁判id
        Integer mainId = gUmpiresMapper.getMainUmpireId(umpires.getPlaygroundId());

        HashMap<String, Object> GUmpiresMap = getStringObjectHashMap(jwt, umpires, GUmpiresId, mainId);
        ObjectMapper objectMapper = new ObjectMapper();
        String json = null;
        try {
            json = objectMapper.writeValueAsString(GUmpiresMap);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        redisTemplate.opsForValue().set("login_GUmpires:" + GUmpiresId, json, Duration.ofHours(4L));

        /**
         * 4.返回
         */
        LoginReturn loginReturn = new LoginReturn(authParamsDto.getSysteamType(), umpires, GUmpiresMap);
        return loginReturn;
    }

}
