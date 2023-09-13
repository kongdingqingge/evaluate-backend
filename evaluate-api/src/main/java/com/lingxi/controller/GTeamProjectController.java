package com.lingxi.controller;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lingxi.convert.IGenerator;
import com.lingxi.enums.AppHttpCodeEnum;
import com.lingxi.model.param.AddTeamProjectPram;
import com.lingxi.model.param.UpdateTeamProjectPram;
import com.lingxi.model.po.GProjects;
import com.lingxi.model.po.GTeamProject;
import com.lingxi.model.vo.TeamProjectVo;
import com.lingxi.result.ResponseResult;
import com.lingxi.service.GProjectsService;
import com.lingxi.service.GTeamProjectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 队伍项目表 前端控制器
 * </p>
 *
 * @author tan
 */
@Slf4j
@RestController
@RequestMapping("/gTeamProject")
public class GTeamProjectController {

    @Autowired
    private GTeamProjectService gTeamProjectService;

    @Autowired
    private IGenerator iGenerator;

    @Autowired
    private GProjectsService gProjectsService;


    /**
     * 1.查询所有队伍参数的项目
     *
     * @return
     */
    @GetMapping("/getAllTeam")
    @PreAuthorize("@TAN.hasAuthority('admin')")
    public ResponseResult getAllTeam() {
        List<TeamProjectVo> allTeamProject = gTeamProjectService.getAllTeamProject();
        if (allTeamProject.isEmpty()) {
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST);
        } else {
            return ResponseResult.okResult(allTeamProject);
        }
    }


    /**
     * 2.修改队伍参加项目
     *
     * @param updateTeamProjectPram
     * @return
     */
    @PostMapping("/update")
    @PreAuthorize("@TAN.hasAuthority('admin')")
    public ResponseResult updateTeamProject(@RequestBody @Valid UpdateTeamProjectPram updateTeamProjectPram) {
        GTeamProject byId = gTeamProjectService.getById(updateTeamProjectPram.getId());
        if (ObjectUtil.isNull(byId)) {
            return ResponseResult.errorResult(400, "队伍为参加该项目,无法修改");
        }
        LambdaQueryWrapper<GProjects> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(GProjects::getPName, updateTeamProjectPram.getProjectName());
        GProjects one = gProjectsService.getOne(wrapper);
        if (ObjectUtil.isNull(one)) {
            return ResponseResult.errorResult(400, "您输入的比赛的项目不存在");
        }
        GTeamProject gTeamProject = iGenerator.toTeamProject(updateTeamProjectPram);
        gTeamProject.setPId(one.getId());
        boolean success = gTeamProjectService.updateById(gTeamProject);
        if (!success) {
            return ResponseResult.errorResult(400, "修改失败");
        }
        return ResponseResult.okResult(200, "修改成功");
    }


    /**
     * 3.id删除队伍参加项目
     *
     * @param teamProjectId
     * @return
     */
    @GetMapping("/del/{teamProjectId}")
    @PreAuthorize("@TAN.hasAuthority('admin')")
    public ResponseResult delTeamProject(@PathVariable String teamProjectId) {
        if (teamProjectId == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_REQUIRE);
        }
        boolean success = gTeamProjectService.removeById(teamProjectId);
        if (success) {
            return ResponseResult.okResult(200, "删除成功");
        } else {
            return ResponseResult.okResult(400, "删除失败");
        }
    }


    /**
     * 4.添加队伍参加项目
     *
     * @param addTeamProjectPram
     * @return
     */
    @PostMapping("/addTeamProject")
    @PreAuthorize("@TAN.hasAuthority('admin')")
    public ResponseResult addTeamProject(@RequestBody AddTeamProjectPram addTeamProjectPram) {
        //项目名称查询项目
        LambdaQueryWrapper<GProjects> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(GProjects::getPName, addTeamProjectPram.getProjectName());
        GProjects one = gProjectsService.getOne(lambdaQueryWrapper);
        if (ObjectUtil.isNull(one)) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PROJECT_NOT_EXIST);
        }
        GTeamProject gTeamProject = iGenerator.toAddTeamProject(addTeamProjectPram);
        gTeamProject.setPId(one.getId());
        boolean success = gTeamProjectService.save(gTeamProject);
        if (success) {
            return ResponseResult.okResult(200, "添加成功");
        } else {
            return ResponseResult.errorResult(400, "添加失败");
        }
    }

    /**
     * 5.根据队伍ID和项目ID查询项目
     *
     * @return com.lingxi.result.ResponseResult
     * @author Cyou
     */
    @GetMapping("/getTeamProject/{projectId}")
    @PreAuthorize("@TAN.hasAuthority('admin')")
    public ResponseResult getTeamProject(@PathVariable() Integer projectId) {
        LambdaQueryWrapper<GTeamProject> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(GTeamProject::getPId, projectId);
        List<GTeamProject> teamProject = gTeamProjectService.list(queryWrapper);
        if (ObjectUtil.isEmpty(teamProject)) {
            return ResponseResult.errorResult(AppHttpCodeEnum.SERVER_ERROR, "暂无队伍");
        }
        return ResponseResult.okResult(teamProject);
    }
}