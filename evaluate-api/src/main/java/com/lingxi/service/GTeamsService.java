package com.lingxi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lingxi.model.po.GTeams;
import com.lingxi.result.ResponseResult;

import java.util.List;

/**
 * <p>
 * 队伍表 服务类
 * </p>
 *
 * @author tan
 * @since 2023-09-05
 */
public interface GTeamsService extends IService<GTeams> {

    ResponseResult addTeams(GTeams gTeams);

    ResponseResult updateTeams(GTeams gTeams);

    ResponseResult deleteTeams(Integer teamId);

    List<GTeams> getTeamsAll(Integer pageNum, Integer pageSize);

    boolean deleteTeamAndProject();

}