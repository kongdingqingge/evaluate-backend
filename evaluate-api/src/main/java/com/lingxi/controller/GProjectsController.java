package com.lingxi.controller;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lingxi.convert.IGenerator;
import com.lingxi.enums.AppHttpCodeEnum;
import com.lingxi.model.param.AddProjectPram;
import com.lingxi.model.param.UpdateProjectPram;
import com.lingxi.model.po.GMatches;
import com.lingxi.model.po.GProjects;
import com.lingxi.model.po.GUnits;
import com.lingxi.result.ResponseResult;
import com.lingxi.service.GMatchesService;
import com.lingxi.service.GProjectsService;
import com.lingxi.service.GUnitsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 项目表 前端控制器
 * </p>
 *
 * @author tan
 */
@Slf4j
@RestController
@RequestMapping("/gProjects")
public class GProjectsController {

    @Autowired
    private GProjectsService gProjectsService;

    @Autowired
    private GMatchesService gMatchesService;

    @Autowired
    private IGenerator iGenerator;

    @Autowired
    private GUnitsService gUnitsService;

    /**
     * 1.修改项目
     *
     * @param updateProjectParm
     * @return
     */
    @PostMapping("/updateProject")
    @PreAuthorize("@TAN.hasAuthority('admin')")
    public ResponseResult updateProject(@RequestBody @Valid UpdateProjectPram updateProjectParm) {
        GProjects gProjects = gProjectsService.getById(updateProjectParm.getId());
        if (ObjectUtil.isNull(gProjects)) {
            return ResponseResult.errorResult(400, "项目不存在,无法修改");
        }
        GProjects project = iGenerator.toProject(updateProjectParm);
        boolean update = gProjectsService.updateById(project);
        if (update) {
            return ResponseResult.okResult(200, "修改成功");
        } else {
            return ResponseResult.errorResult(400, "修改失败");
        }
    }


    /**
     * 2.删除项目
     *
     * @param projectId
     * @param matchId
     * @return
     */
    @GetMapping("/delProject/{matchId}/{unitId}/{projectId}")
    @PreAuthorize("@TAN.hasAuthority('admin')")
    public ResponseResult deleteProject(@PathVariable Integer projectId, @PathVariable Integer matchId, @PathVariable Integer unitId) {
        // 获取比赛信息
        GMatches match = gMatchesService.getById(matchId);
        if (matchHasStarted(match)) {
            return ResponseResult.errorResult(400, "比赛已开始，无法删除项目");
        }
        // 获取单位信息
        GUnits gUnits = gUnitsService.getById(unitId);
        if (gUnits != null) {
            Integer projectCount = gUnits.getProjectCount();
            // 删除项目
            if (deleteProjectById(projectId)) {
                gUnits.setProjectCount(projectCount - 1);
                gUnitsService.updateById(gUnits);
                return ResponseResult.okResult(200, "删除成功");
            } else {
                return ResponseResult.errorResult(400, "删除失败");
            }
        } else {
            return ResponseResult.errorResult(400, "单位不存在");
        }
    }

    // 检查比赛是否已开始
    private boolean matchHasStarted(GMatches match) {
        return match != null && match.getMStatus() == 1;
    }

    // 删除项目
    private boolean deleteProjectById(Integer projectId) {
        return gProjectsService.removeById(projectId);
    }


    /**
     * 3.查询单个项目
     */
    @GetMapping("/getOne/{projectId}")
    @PreAuthorize("@TAN.hasAuthority('admin')")
    public ResponseResult getOneById(@PathVariable Integer projectId) {
        if (projectId == null) {
            ResponseResult.errorResult(AppHttpCodeEnum.PARAM_REQUIRE);
        }
        GProjects byId = gProjectsService.getById(projectId);
        if (ObjectUtil.isNull(byId)) {
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST);
        } else {
            return ResponseResult.okResult(byId);
        }
    }

    /**
     * 4.分页查询项目
     *
     * @param unitId
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/getALlProject/{unitId}/{pageNum}/{pageSize}")
    @PreAuthorize("@TAN.hasAuthority('admin')")
    public ResponseResult getAllProjects(@PathVariable Integer unitId, @PathVariable Integer pageNum, @PathVariable Integer pageSize) {
        Page<GProjects> gProjectsPage = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<GProjects> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(GProjects::getUnitId, unitId);
        Page<GProjects> page = gProjectsService.page(gProjectsPage, wrapper);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("total", page.getTotal());
        resultMap.put("records", page.getRecords());
        return ResponseResult.okResult(resultMap);
    }


    /**
     * 5.添加项目
     *
     * @param addProjectPram
     * @return
     */
    @PostMapping("/addProject")
    @PreAuthorize("@TAN.hasAuthority('admin')")
    public ResponseResult addProject(@RequestBody @Valid AddProjectPram addProjectPram) {
        GProjects gProjects = iGenerator.addProjectPramtoProject(addProjectPram);
        boolean save = gProjectsService.save(gProjects);
        if (!save) {
            return ResponseResult.errorResult(400, "添加失败");
        }
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }
}
