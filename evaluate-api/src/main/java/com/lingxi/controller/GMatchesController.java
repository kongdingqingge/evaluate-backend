package com.lingxi.controller;

import com.lingxi.convert.IGenerator;
import com.lingxi.enums.AppHttpCodeEnum;
import com.lingxi.model.dto.ImportMatchDto;
import com.lingxi.model.dto.ImportProjectsDto;
import com.lingxi.model.param.UpdateMatchParm;
import com.lingxi.model.po.GMatches;
import com.lingxi.model.po.GPlayground;
import com.lingxi.model.po.GProjects;
import com.lingxi.model.po.GUnits;
import com.lingxi.model.vo.GunitVo;
import com.lingxi.model.vo.MatchVo;
import com.lingxi.result.ResponseResult;
import com.lingxi.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <p>
 * 赛程表 前端控制器
 * </p>
 *
 * @author tan
 */
@Slf4j
@CrossOrigin("*")
@RestController
@RequestMapping("/gMatches")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Api(tags = "赛程相关接口")
public class GMatchesController {

    private final IGenerator iGenerator;

    private final GMatchesService matchesService;

    private final GUnitsService unitsService;

    private final GPlaygroundService gPlaygroundService;

    private final GUnitsService gUnitsService;

    private final GProjectsService projectsService;

    private final GUmpiresService umpiresService;


    /**
     * 1.表格导入赛程
     */
    @PostMapping("/add")
    @ApiImplicitParam(name = "matchDto", value = "赛程dto")
    @Transactional
    @PreAuthorize("@TAN.hasAuthority('admin')")
    @ApiOperation(value = "添加赛程", notes = "<span style='color:red'>描述:</span>&nbsp;用于添加赛程")
    public ResponseResult saveMatch(@RequestParam("file") MultipartFile file) {
        List<GMatches> list = matchesService.list();
        if (!list.isEmpty()) {
            matchesService.clearAll();
        }
        //1.导入
        ImportMatchDto importMatchDto = matchesService.importMatch(file);
        //2.添加
        GMatches gMatch = iGenerator.importMatchDtotoGMatch(importMatchDto);
        boolean mSave = matchesService.save(gMatch);
        if (mSave) {
            Integer matchId = gMatch.getId();
            //2.添加场地,返回场地集合流
            importMatchDto.getImportPlayGroundDto().stream()
                    .flatMap(groundDto -> {
                        GPlayground gPlayground = iGenerator.playGroundDtotoPlayGround(groundDto);
                        gPlayground.setMatchId(matchId);
                        boolean gPlaygroundSave = gPlaygroundService.save(gPlayground);
                        if (gPlaygroundSave) {
                            Integer playgroundId = gPlayground.getId();
                            //3.添加单元组,返回单元集合流
                            return groundDto.getImportUnitDtoList().stream()
                                    .flatMap(unitDto -> {
                                        GUnits units = iGenerator.importUnittoUnits(unitDto);
                                        units.setPlaygroundId(playgroundId);
                                        boolean uSave = gUnitsService.save(units);
                                        if (uSave) {
                                            Integer unitsId = units.getId();
                                            //4.添加单元组下的所有项目,
                                            List<ImportProjectsDto> collect = unitDto.getImportProjectsDtoList().stream()
                                                    .map(eProjectDto -> {
                                                                GProjects gProject = iGenerator.ImportProjectstoProject(eProjectDto);
                                                                gProject.setUnitId(unitsId);
                                                                projectsService.save(gProject);
                                                                return eProjectDto;
                                                            }
                                                    )
                                                    .collect(Collectors.toList());
                                            return collect.stream();
                                        } else {
                                            return Stream.empty();
                                        }
                                    });
                        } else {
                            return Stream.empty();
                        }
                    })
                    .collect(Collectors.toList());
            return new ResponseResult(200, "赛程添加成功");
        }
        return new ResponseResult(400, "赛程添加失败");
    }


    /**
     * 3.查询赛程及数据
     */
    @GetMapping("/getMatch")
    @PreAuthorize("@TAN.hasAuthority('admin')")
    @ApiOperation(value = "查询赛程", notes = "用于查询所有赛程相关数据")
    public ResponseResult getMatch() {
        MatchVo matches = matchesService.getAllMatches();
        if (matches != null) {
            return ResponseResult.okResult(matches);
        } else {
            return ResponseResult.errorResult(400, "暂无赛程");
        }
    }


    /**
     * 4.修改赛程
     */
    @PostMapping("/updateMatch")
    @PreAuthorize("@TAN.hasAuthority('admin')")
    @Transactional
    public ResponseResult updateMatch(@RequestBody @Valid UpdateMatchParm updateMatch) {
        if (updateMatch == null) {
            return ResponseResult.errorResult(400, "无效的参数");
        }
        GMatches gMatches = iGenerator.toGMatch(updateMatch);
        boolean update = matchesService.updateById(gMatches);
        if (update) {
            return ResponseResult.okResult(200, "修改成功");
        } else {
            return ResponseResult.errorResult(400, "修改失败");
        }
    }


    /**
     * 5.根据id删除赛程,及相关所有数据
     */
    @GetMapping("/delete/{matchId}")
    @PreAuthorize("@TAN.hasAuthority('admin')")
    public ResponseResult deleteMatch(@PathVariable Integer matchId) {
        if (matchId == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_REQUIRE);
        }
        GMatches match = matchesService.getById(matchId);
        
        if (match.getMStatus() == 1) {
            return ResponseResult.errorResult(400, "比赛已开始,无法删除");
        }
        boolean flag = matchesService.deleteMatch(matchId);
        return flag ? ResponseResult.okResult(200, "删除赛程成功")
                : ResponseResult.errorResult(400, AppHttpCodeEnum.MATCH_NOT_EXIST.getMessage());
    }


    /**
     * 6.根据赛程id查询所有单元
     */
    @GetMapping("/getUnits/{groundPlayId}")
    @ApiImplicitParam(name = "matchId", value = "赛程id")
    @PreAuthorize("@TAN.hasAuthority('admin')")
    @ApiOperation(value = "查询所有单元", notes = "<span style='color:red'>描述:</span>&nbsp;根据赛程id查询所有单元")
    public ResponseResult getUnitsByMatchId(@PathVariable Integer groundPlayId) {
        if (groundPlayId == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_REQUIRE);
        }
        Optional<List<GunitVo>> unitVoList = Optional.ofNullable(matchesService.getUnitsByGId(groundPlayId));
        return unitVoList.map(ResponseResult::okResult)
                .orElse(ResponseResult.errorResult(AppHttpCodeEnum.NO_UNITS));
    }
}