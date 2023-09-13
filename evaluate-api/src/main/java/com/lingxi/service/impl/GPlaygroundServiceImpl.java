package com.lingxi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lingxi.mapper.GMatchesMapper;
import com.lingxi.mapper.GPlaygroundMapper;
import com.lingxi.model.po.GPlayground;
import com.lingxi.service.GPlaygroundService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 场地 服务实现类
 * </p>
 *
 * @author tan
 */
@Slf4j
@Service
public class GPlaygroundServiceImpl extends ServiceImpl<GPlaygroundMapper, GPlayground> implements GPlaygroundService {

    @Autowired
    private GMatchesMapper gMatchesMapper;

    @Override
    public Boolean delPlayGround(Integer playGroundId) {
        return gMatchesMapper.dePlayGroundById(playGroundId);
    }
}
