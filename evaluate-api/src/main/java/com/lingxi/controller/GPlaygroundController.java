package com.lingxi.controller;

import cn.hutool.core.util.ObjectUtil;
import com.lingxi.convert.IGenerator;
import com.lingxi.enums.AppHttpCodeEnum;
import com.lingxi.model.param.UpdatePlayGroundPram;
import com.lingxi.model.po.GMatches;
import com.lingxi.model.po.GPlayground;
import com.lingxi.result.ResponseResult;
import com.lingxi.service.GMatchesService;
import com.lingxi.service.GPlaygroundService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * <p>
 * 场地 前端控制器
 * </p>
 *
 * @author tan
 */
@Slf4j
@RestController
@RequestMapping("/gPlayground")
public class GPlaygroundController {

    @Autowired
    private GPlaygroundService gPlaygroundService;

    @Autowired
    private GMatchesService gMatchesService;


    @Autowired
    private IGenerator iGenerator;


    /**
     * 1.修改场地
     *
     * @param updatePlayGroundPram
     * @return
     */
    @PostMapping("/updatePlayGround")
    @PreAuthorize("@TAN.hasAuthority('admin')")
    public ResponseResult updatePlayGround(@RequestBody @Valid UpdatePlayGroundPram updatePlayGroundPram) {
        GPlayground gPlayground = gPlaygroundService.getById(updatePlayGroundPram.getId());
        if (ObjectUtil.isNull(gPlayground)) {
            return ResponseResult.errorResult(400, "场地不存在,无法修改");
        }
        GPlayground playGround = iGenerator.toPlayGround(updatePlayGroundPram);
        boolean update = gPlaygroundService.updateById(playGround);
        if (update) {
            return ResponseResult.okResult(200, "修改成功");
        } else {
            return ResponseResult.errorResult(400, "修改失败");
        }

    }

    /**
     * 2.删除场地
     *
     * @param playGroundId
     * @return
     */
    @GetMapping("/delPlayGround/{matchId}/{playGroundId}")
    @PreAuthorize("@TAN.hasAuthority('admin')")
    public ResponseResult delUpdatePlayGround(@PathVariable Integer playGroundId, @PathVariable Integer matchId) {
        GMatches match = gMatchesService.getById(matchId);
        if (match.getMStatus() == 1) {
            return ResponseResult.errorResult(400, "比赛已开始,无法删除");
        }
        Boolean aBoolean = gPlaygroundService.delPlayGround(playGroundId);
        if (aBoolean) {
            return ResponseResult.okResult(200, "删除成功");
        } else {
            return ResponseResult.errorResult(400, "删除失败");
        }

    }

    /**
     * 3.查询单个场地
     */
    @GetMapping("/getOne/{playGroundId}")
    @PreAuthorize("@TAN.hasAuthority('admin')")
    public ResponseResult getOneById(@PathVariable Integer playGroundId) {
        if (playGroundId == null) {
            ResponseResult.errorResult(AppHttpCodeEnum.PARAM_REQUIRE);
        }
        GPlayground byId = gPlaygroundService.getById(playGroundId);
        if (ObjectUtil.isNull(byId)) {
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST);
        } else {
            return ResponseResult.okResult(byId);
        }
    }


}
