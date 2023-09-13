package com.lingxi.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lingxi.enums.AppHttpCodeEnum;
import com.lingxi.mapper.GUnitsMapper;
import com.lingxi.model.po.GUnits;
import com.lingxi.result.ResponseResult;
import com.lingxi.service.GUnitsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 单元表 服务实现类
 * </p>
 *
 * @author tan
 */
@Slf4j
@Service
public class GUnitsServiceImpl extends ServiceImpl<GUnitsMapper, GUnits> implements GUnitsService {

    @Autowired
    private GUnitsMapper gUnitsMapper;

    @Override
    public ResponseResult updateUnits(GUnits gUnits) {
        if (!ObjectUtil.isNotNull(gUnits)) {
            return ResponseResult.errorResult(AppHttpCodeEnum.AUTH_DATA_ERROR, "空数据");
        }
        int i = gUnitsMapper.updateUnits(gUnits);
        if (i > 0) {
            return new ResponseResult(AppHttpCodeEnum.SUCCESS.getCode(), "修改成功");
        } else {
            return ResponseResult.errorResult(AppHttpCodeEnum.SERVER_ERROR, "修改失败");
        }
    }

    @Override
    public ResponseResult addUnits(GUnits gUnits) {
        if (!ObjectUtil.isNotNull(gUnits)) {
            return ResponseResult.errorResult(AppHttpCodeEnum.SERVER_ERROR, "请勿添加空数据");
        }
        int i = gUnitsMapper.addUnits(gUnits);
        if (i > 0) {
            return new ResponseResult(AppHttpCodeEnum.SUCCESS.getCode(), "添加成功");
        } else {
            return ResponseResult.errorResult(AppHttpCodeEnum.SERVER_ERROR, "添加失败");
        }
    }

    @Override
    public ResponseResult deleteUnits(List<Integer> ids) {
        for (Integer id : ids) {
            int i = gUnitsMapper.deleteUnitAndRelated(id);
            if (i <= 0) {
                return new ResponseResult(AppHttpCodeEnum.SERVER_ERROR.getCode(), "删除失败");
            }
        }
        return new ResponseResult(AppHttpCodeEnum.SUCCESS.getCode(), "删除成功");
    }

    @Override
    public List<GUnits> getUnitsAll(Integer pageNum, Integer pageSize) {
        Page<GUnits> gUnitsPage = new Page<>(pageNum, pageSize);
        page(gUnitsPage, null);
        return gUnitsPage.getRecords();
    }

    @Override
    public List<GUnits> getAllUnitByMatch(Integer playGroundId) {
        return gUnitsMapper.getAllByMatchId(playGroundId);
    }
}