package com.lingxi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lingxi.model.param.TeamProjectHistory;
import com.lingxi.model.po.GUmpires;
import com.lingxi.model.vo.GScoreUmpireVo;
import com.lingxi.result.ResponseResult;

import java.util.List;

/**
 * <p>
 * 裁判表 服务类
 * </p>
 *
 * @author tan
 * @since 2023-09-05
 */
public interface GUmpiresService extends IService<GUmpires> {

    ResponseResult getUmpiresAll();

    ResponseResult getUmpiresOne(Integer id);

    ResponseResult deleteUmpires(Integer id);

    ResponseResult addUmpires(GUmpires gUmpires);

    List<GScoreUmpireVo> getHistory(TeamProjectHistory teamProjectHistory);

    ResponseResult getMarkUmpires(Integer playgroundId, Integer unitId, Integer projectId);
}