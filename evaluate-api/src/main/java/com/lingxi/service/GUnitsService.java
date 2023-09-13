package com.lingxi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lingxi.model.po.GUnits;
import com.lingxi.result.ResponseResult;

import java.util.List;

/**
 * <p>
 * 单元表 服务类
 * </p>
 *
 * @author tan
 * @since 2023-09-05
 */
public interface GUnitsService extends IService<GUnits> {

    ResponseResult updateUnits(GUnits units);

    ResponseResult addUnits(GUnits gUnits);

    ResponseResult deleteUnits(List<Integer> ids);

    List<GUnits> getUnitsAll(Integer pageNum, Integer pageSize);

    List<GUnits> getAllUnitByMatch(Integer matchId);
}