package com.lingxi.controller;

import com.lingxi.enums.AppHttpCodeEnum;
import com.lingxi.model.po.GUnits;
import com.lingxi.result.ResponseResult;
import com.lingxi.service.GUnitsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 单元表 前端控制器
 * </p>
 *
 * @author tan
 */
@Slf4j
@RestController
@RequestMapping("gUnits")
public class GUnitsController {

    @Autowired
    private GUnitsService gUnitsService;

    /**
     * 查询单个单元
     *
     * @param id
     * @return com.lingxi.result.ResponseResult
     * @author Cyou
     */
    @GetMapping("/getOne/{id}")
    @PreAuthorize("@TAN.hasAuthority('admin')")
    public ResponseResult getUnitsOne(@PathVariable("id") Integer id) {
        GUnits byId = gUnitsService.getById(id);
        return new ResponseResult(AppHttpCodeEnum.SUCCESS.getCode(), AppHttpCodeEnum.SUCCESS.getMessage(), byId);
    }

    /**
     * 查询全部单元
     *
     * @return com.lingxi.result.ResponseResult
     * @author Cyou
     */
    @GetMapping("/getAll/{pageNum}/{pageSize}")
    @PreAuthorize("@TAN.hasAuthority('admin')")
    public ResponseResult getUnitsAll(@PathVariable("pageNum") Integer pageNum, @PathVariable("pageSize") Integer pageSize) {
        List<GUnits> units = gUnitsService.getUnitsAll(pageNum, pageSize);
        return new ResponseResult(AppHttpCodeEnum.SUCCESS.getCode(), AppHttpCodeEnum.SUCCESS.getMessage(), units);
    }

    /**
     * 修改单元
     *
     * @param units
     * @return com.lingxi.result.ResponseResult
     * @author Cyou
     */
    @PostMapping("/updateUnits")
    @PreAuthorize("@TAN.hasAuthority('admin')")
    public ResponseResult updateUnits(@RequestBody @Valid GUnits units) {
        return gUnitsService.updateUnits(units);
    }

    /**
     * 添加单元
     *
     * @param gUnits
     * @return com.lingxi.result.ResponseResult
     * @author Cyou
     */
    @PostMapping("/addTeams")
    @PreAuthorize("@TAN.hasAuthority('admin')")
    public ResponseResult addUnits(@RequestBody @Valid GUnits gUnits) {
        return gUnitsService.addUnits(gUnits);
    }

    /**
     * 删除单元
     *
     * @param ids
     * @return com.lingxi.result.ResponseResult
     * @author Cyou
     */
    @GetMapping("/deleteUnits/{ids}")
    @PreAuthorize("@TAN.hasAuthority('admin')")
    public ResponseResult deleteUnits(@PathVariable List<Integer> ids) {
        return gUnitsService.deleteUnits(ids);
    }
}