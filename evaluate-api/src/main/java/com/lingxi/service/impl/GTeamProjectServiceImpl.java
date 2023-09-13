package com.lingxi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lingxi.mapper.GTeamProjectMapper;
import com.lingxi.model.po.GTeamProject;
import com.lingxi.model.vo.TeamProjectVo;
import com.lingxi.service.GTeamProjectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 队伍项目表 服务实现类
 * </p>
 *
 * @author tan
 */
@Slf4j
@Service
public class GTeamProjectServiceImpl extends ServiceImpl<GTeamProjectMapper, GTeamProject> implements GTeamProjectService {

    @Autowired
    private GTeamProjectMapper gTeamProjectMapper;

    @Override
    public List<TeamProjectVo> getAllTeamProject() {
        return gTeamProjectMapper.getTeamProjects();
    }
}
