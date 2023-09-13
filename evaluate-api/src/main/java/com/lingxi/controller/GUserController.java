package com.lingxi.controller;


import com.lingxi.consts.MyConstants;
import com.lingxi.enums.AppHttpCodeEnum;
import com.lingxi.exception.CustomException;
import com.lingxi.model.dto.AuthParamsDto;
import com.lingxi.model.dto.LoginReturn;
import com.lingxi.model.po.GUser;
import com.lingxi.model.vo.UserRedisVo;
import com.lingxi.result.ResponseResult;
import com.lingxi.service.AuthService;
import com.lingxi.service.GUserService;
import com.lingxi.utils.RedisCache;
import com.lingxi.utils.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author tan
 */
@Slf4j
@RestController
@RequestMapping("/gUser")
public class GUserController {
    @Autowired
    @Qualifier("user_authservice")
    private AuthService userAuthService;
    @Resource
    private RedisCache redisCache;

    @Autowired
    private GUserService gUserService;

    @GetMapping("/getUser/{uId}")
    public ResponseResult getUserMessage(@PathVariable Integer uId) {
        GUser user = gUserService.getById(uId);
        user.setPassword("");
        return ResponseResult.errorResult(200, user);
    }

    @PostMapping("/login")
    public ResponseResult login(@RequestBody AuthParamsDto authParamsDto) {
        try {
            LoginReturn loginReturn = userAuthService.execute(authParamsDto);
            return ResponseResult.okResult(loginReturn.getMessageMap());
        } catch (CustomException ex) {
            throw new CustomException(AppHttpCodeEnum.USER_DATA_NOT_EXIST);
        }
    }


    @GetMapping("/login_out")
    public ResponseResult loginOut() {
        //1.根据SecurityContextHolder中的用户id得到redis的键
        UserRedisVo onlineUser = SecurityUtil.getOnlineUser();
        String key = "login_" + MyConstants.LOGIN_USER + ":" + onlineUser.getUserId();
        //2.删除redis中key的值
        try {
            redisCache.deleteObject(key);
        } catch (Exception e) {
            throw new CustomException(AppHttpCodeEnum.REDIS_USER_DATA_NOT_EXIST);
        }
        return ResponseResult.okResult(200, "退出成功");
    }


}
