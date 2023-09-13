package com.lingxi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lingxi.mapper.GProjectsMapper;
import com.lingxi.model.po.GProjects;
import com.lingxi.service.GProjectsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 项目表 服务实现类
 * </p>
 *
 * @author tan
 */
@Slf4j
@Service
public class GProjectsServiceImpl extends ServiceImpl<GProjectsMapper, GProjects> implements GProjectsService {

    @Autowired
    private GProjectsMapper gProjectsMapper;

    @Override
    public List<GProjects> getAllProjectByUinitId(Integer unitId) {
        return gProjectsMapper.getAllprojectByUintId(unitId);
    }

}
