package com.lingxi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lingxi.mapper.GUserMapper;
import com.lingxi.model.po.GUser;
import com.lingxi.service.GUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author tan
 */
@Slf4j
@Service
public class GUserServiceImpl extends ServiceImpl<GUserMapper, GUser> implements GUserService {

}
