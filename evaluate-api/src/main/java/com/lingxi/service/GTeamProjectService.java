package com.lingxi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lingxi.model.po.GTeamProject;
import com.lingxi.model.vo.TeamProjectVo;

import java.util.List;

/**
 * <p>
 * 队伍项目表 服务类
 * </p>
 *
 * @author tan
 * @since 2023-09-05
 */
public interface GTeamProjectService extends IService<GTeamProject> {

    List<TeamProjectVo> getAllTeamProject();
}
