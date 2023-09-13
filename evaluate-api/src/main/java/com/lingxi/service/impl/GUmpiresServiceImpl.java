package com.lingxi.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lingxi.enums.AppHttpCodeEnum;
import com.lingxi.mapper.GUmpiresMapper;
import com.lingxi.model.param.TeamProjectHistory;
import com.lingxi.model.po.GUmpires;
import com.lingxi.model.vo.GScoreUmpireVo;
import com.lingxi.model.vo.MarkGumpiresVo;
import com.lingxi.model.vo.PlayGroundUmpiresVo;
import com.lingxi.result.ResponseResult;
import com.lingxi.service.GUmpiresService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;


/**
 * <p>
 * 裁判表 服务实现类
 * </p>
 *
 * @author tan
 */
@Slf4j
@Service
public class GUmpiresServiceImpl extends ServiceImpl<GUmpiresMapper, GUmpires> implements GUmpiresService {

    @Autowired
    private GUmpiresMapper gUmpiresMapper;

    @Override
    public ResponseResult getUmpiresAll() {
        List<PlayGroundUmpiresVo> umpiresAll = gUmpiresMapper.getUmpiresAll();
        return new ResponseResult(AppHttpCodeEnum.SUCCESS.getCode(), umpiresAll);
    }

    @Override
    public ResponseResult getUmpiresOne(Integer id) {
        GUmpires umpiresOne = gUmpiresMapper.getUmpires(id);
        return new ResponseResult(AppHttpCodeEnum.SUCCESS.getCode(), umpiresOne);
    }

    @Override
    public ResponseResult deleteUmpires(Integer id) {
        int i = gUmpiresMapper.deleteUmpires(id);
        if (i <= 0) {
            return new ResponseResult(AppHttpCodeEnum.SERVER_ERROR.getCode(), "删除失败");
        }
        return new ResponseResult(AppHttpCodeEnum.SUCCESS.getCode(), "删除成功");
    }

    @Override
    public ResponseResult addUmpires(GUmpires gUmpires) {
        if (!ObjectUtil.isNotNull(gUmpires)) {
            return ResponseResult.errorResult(AppHttpCodeEnum.SERVER_ERROR, "请勿添加空数据");
        }
        int i = gUmpiresMapper.addUmpires(gUmpires);
        if (i > 0) {
            return new ResponseResult(AppHttpCodeEnum.SUCCESS.getCode(), "添加成功");
        } else {
            return ResponseResult.errorResult(AppHttpCodeEnum.SERVER_ERROR, "添加失败");
        }
    }

    @Override
    public List<GScoreUmpireVo> getHistory(TeamProjectHistory teamProjectHistory) {
        return gUmpiresMapper.getOneAllScore(teamProjectHistory);
    }

    @Override
    public ResponseResult getMarkUmpires(Integer playgroundId, Integer unitId, Integer projectId) {
        List<MarkGumpiresVo> gUmpiresVo = gUmpiresMapper.getMarkUmpires(playgroundId, unitId, projectId);
        if (CollectionUtils.isEmpty(gUmpiresVo)) {
            return new ResponseResult(AppHttpCodeEnum.SERVER_ERROR.getCode(), "暂无数据");
        }
        return new ResponseResult(AppHttpCodeEnum.SUCCESS.getCode(), gUmpiresVo);
    }
}