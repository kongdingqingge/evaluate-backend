package com.lingxi.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lingxi.enums.AppHttpCodeEnum;
import com.lingxi.mapper.GTeamProjectMapper;
import com.lingxi.mapper.GTeamsMapper;
import com.lingxi.model.po.GTeams;
import com.lingxi.result.ResponseResult;
import com.lingxi.service.GTeamsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 队伍表 服务实现类
 * </p>
 *
 * @author tan
 */
@Slf4j
@Service
public class GTeamsServiceImpl extends ServiceImpl<GTeamsMapper, GTeams> implements GTeamsService {

    @Autowired
    private GTeamsMapper gTeamsMapper;

    @Autowired
    private GTeamProjectMapper gTeamProjectMapper;

    @Override
    public ResponseResult addTeams(GTeams gTeams) {
        if (!ObjectUtil.isNotNull(gTeams)) {
            return ResponseResult.errorResult(AppHttpCodeEnum.SERVER_ERROR, "请勿添加空数据");
        }
        int i = gTeamsMapper.addTeams(gTeams);
        if (i > 0) {
            return new ResponseResult(AppHttpCodeEnum.SUCCESS.getCode(), "添加成功");
        } else {
            return ResponseResult.errorResult(AppHttpCodeEnum.SERVER_ERROR, "添加失败");
        }
    }

    @Override
    public ResponseResult updateTeams(GTeams gTeams) {
        if (!ObjectUtil.isNotNull(gTeams)) {
            return ResponseResult.errorResult(AppHttpCodeEnum.SERVER_ERROR, "空数据");
        }
        int i = gTeamsMapper.updateTeams(gTeams);
        if (i > 0) {
            return new ResponseResult(AppHttpCodeEnum.SUCCESS.getCode(), "修改成功");
        } else {
            return ResponseResult.errorResult(AppHttpCodeEnum.SERVER_ERROR, "修改失败");
        }
    }

    @Override
    public ResponseResult deleteTeams(Integer teamId) {
        int i = gTeamsMapper.deleteTeams(teamId);
        if (i <= 0) {
            return new ResponseResult(AppHttpCodeEnum.SERVER_ERROR.getCode(), "删除失败");
        }
        return new ResponseResult(AppHttpCodeEnum.SUCCESS.getCode(), "删除成功");
    }

    @Override
    public List<GTeams> getTeamsAll(Integer pageNum, Integer pageSize) {
        Page<GTeams> gTeamsPage = new Page<>(pageNum, pageSize);
        page(gTeamsPage, null);
        return gTeamsPage.getRecords();
    }

    @Override
    public boolean deleteTeamAndProject() {
        int delete = gTeamsMapper.delete(null);
        int delete1 = gTeamProjectMapper.delete(null);
        if (delete > 0 && delete1 > 0) {
            return true;
        }
        return false;
    }
}