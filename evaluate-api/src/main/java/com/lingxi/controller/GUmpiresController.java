package com.lingxi.controller;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lingxi.consts.MyConstants;
import com.lingxi.convert.IGenerator;
import com.lingxi.enums.AppHttpCodeEnum;
import com.lingxi.exception.CustomException;
import com.lingxi.model.dto.AuthParamsDto;
import com.lingxi.model.dto.LoginReturn;
import com.lingxi.model.param.TeamProjectHistory;
import com.lingxi.model.param.UpdateTeamProject;
import com.lingxi.model.param.UpdateUmpirePram;
import com.lingxi.model.po.*;
import com.lingxi.model.vo.GScoreUmpireVo;
import com.lingxi.model.vo.UmpireRedisVo;
import com.lingxi.prams.ImportUmpires;
import com.lingxi.result.ResponseResult;
import com.lingxi.service.*;
import com.lingxi.utils.RedisCache;
import com.lingxi.utils.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

import static com.baomidou.mybatisplus.core.toolkit.ObjectUtils.isEmpty;

/**
 * <p>
 * 裁判表 前端控制器
 * </p>
 *
 * @author tan
 */
@Slf4j
@RestController
@RequestMapping("/gUmpires")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class GUmpiresController {

    private final IGenerator iGenerator;
    private final GMatchesService matchesService;
    private final GUnitsService unitsService;
    private final GPlaygroundService gPlaygroundService;
    private final GUnitsService gUnitsService;
    private final GProjectsService projectsService;
    private final GUmpiresService gUmpiresService;
    private final GUmpiresService umpiresService;
    private final GTeamProjectService gTeamProjectService;

    private final GScoreUmpireService gScoreUmpireService;


    @Autowired
    @Qualifier("umpire_auth-service")
    private AuthService userAuthService;

    @Resource
    private RedisCache redisCache;


    /**
     * 1.普通裁判登入
     *
     * @param authParamsDto
     * @return
     */
    @PostMapping("/login")
    public ResponseResult login(@RequestBody AuthParamsDto authParamsDto) {
        try {
            LoginReturn loginReturn = userAuthService.execute(authParamsDto);
            return ResponseResult.okResult(loginReturn.getMessageMap());
        } catch (CustomException ex) {
            return ResponseResult.errorResult(400, ex.getMessage());
        }
    }

    /**
     * 主裁判登入
     *
     * @param authParamsDto
     * @return
     */
    @PostMapping("/mainLogin")
    public ResponseResult mainLogin(@RequestBody AuthParamsDto authParamsDto) {
        try {
            LoginReturn loginReturn = userAuthService.execute(authParamsDto);
            Integer type = (Integer) loginReturn.getMessageMap().get("GUmpires_type");
            if (type != 2) {
                return ResponseResult.errorResult(400, "您不是主裁判无法登入该设备");
            }
            return ResponseResult.okResult(loginReturn.getMessageMap());
        } catch (CustomException ex) {
            return ResponseResult.errorResult(400, ex.getMessage());
        }
    }

    /**
     * 2.裁判退出
     *
     * @return
     */
    @GetMapping("/login_out")
    public ResponseResult loginOut() {
        //1.根据SecurityContextHolder中的用户id得到redis的键
        UmpireRedisVo onlineUmpire = SecurityUtil.getOnlineGUmpires();
        String key = "login_" + MyConstants.LOGING_GUMPIRES + ":" + onlineUmpire.getUmpireId();
        //2.删除redis中key的值
        try {
            redisCache.deleteObject(key);
        } catch (Exception e) {
            throw new CustomException(AppHttpCodeEnum.REDIS_USER_DATA_NOT_EXIST);
        }
        return ResponseResult.okResult(200, "退出成功");
    }


    /**
     * 3.查询所有裁判
     *
     * @return com.lingxi.result.ResponseResult
     * @author Cyou
     */
    @GetMapping("/getAll")
    @PreAuthorize("@TAN.hasAuthority('admin')")
    public ResponseResult getUmpiresAll() {
        return gUmpiresService.getUmpiresAll();
    }

    /**
     * 4.查询单个裁判
     *
     * @param id
     * @return com.lingxi.result.ResponseResult
     * @author Cyou
     */
    @GetMapping("/getOne/{id}")
    @PreAuthorize("@TAN.hasAuthority('admin')")
    public ResponseResult getUmpiresOne(@PathVariable("id") Integer id) {
        return gUmpiresService.getUmpiresOne(id);
    }

    /**
     * 5.修改裁判
     *
     * @param updateUmpirePram
     * @return com.lingxi.result.ResponseResult
     * @author Cyou
     */
    @PostMapping("/updateUmpires")
    @PreAuthorize("@TAN.hasAuthority('admin')")
    public ResponseResult updateUmpires(@RequestBody UpdateUmpirePram updateUmpirePram) {
        GUmpires gUmpires = iGenerator.updateUmpirePramto(updateUmpirePram);
        boolean byId = gUmpiresService.updateById(gUmpires);
        if (byId) {
            return new ResponseResult(AppHttpCodeEnum.SUCCESS.getCode(), "修改成功");
        } else {
            return new ResponseResult(AppHttpCodeEnum.EMPIRES_DATA_NOT_EXIST.getCode(), "修改失败");
        }
    }

    /**
     * 6.删除裁判
     *
     * @param id
     * @return com.lingxi.result.ResponseResult
     * @author Cyou
     */
    @GetMapping("/deleteUmpires/{id}")
    @PreAuthorize("@TAN.hasAuthority('admin')")
    public ResponseResult deleteUmpires(@PathVariable Integer id) {
        return gUmpiresService.deleteUmpires(id);
    }

    /**
     * 7.添加裁判
     *
     * @param gUmpires
     * @return com.lingxi.result.ResponseResult
     * @author Cyou
     */
    @PostMapping("addUmpires")
    public ResponseResult addUmpires(@RequestBody GUmpires gUmpires) {
        boolean save = gUmpiresService.save(gUmpires);
        if (!save) {
            return ResponseResult.errorResult(400, "添加失败");
        }
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
//        return gUmpiresService.addUmpires(gUmpires);
    }


    /**
     * 8.导入裁判
     *
     * @param file
     * @return
     * @throws Exception
     */
    @PostMapping("/importUmpires")
    @Transactional
    @PreAuthorize("@TAN.hasAuthority('admin')")
    public ResponseResult importUmpires(@RequestParam("file") MultipartFile file) throws Exception {
        if (!gUmpiresService.list().isEmpty()) {
            gUmpiresService.remove(null);
        }
        try (InputStream inputStream = file.getInputStream()) {
            ImportParams params = new ImportParams();
            params.setHeadRows(1); // 表头，设置为1
            List<ImportUmpires> importUmpiresList = ExcelImportUtil.importExcel(inputStream, ImportUmpires.class, params);
            List<GUmpires> gUmpiresList = importUmpiresList.stream()
                    .filter(importUmpires -> !isEmpty(importUmpires)) // 假设 isEmpty 方法用于检查是否为空行
                    .map(importUmpires -> {
                        GUmpires gUmpires = mapImportUmpiresToGUmpires(importUmpires);
                        if (importUmpires.getType_String().equals("主裁")) {
                            gUmpires.setType(2);
                        } else {
                            gUmpires.setType(1);
                        }
                        return gUmpires;
                    })
                    .collect(Collectors.toList());
            boolean success = gUmpiresService.saveBatch(gUmpiresList);
            if (success) {
                return ResponseResult.okResult(200, "导入成功");
            } else {
                return ResponseResult.errorResult(400, "导入失败");
            }
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseResult.errorResult(500, "导入过程出错");
        }
    }

    private GUmpires mapImportUmpiresToGUmpires(ImportUmpires importUmpires) {
        LambdaQueryWrapper<GPlayground> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(GPlayground::getPlaygroundName, importUmpires.getPlaygroundName());
        GPlayground one = gPlaygroundService.getOne(queryWrapper);
        ModelMapper modelMapper = new ModelMapper();
        GUmpires gUmpires = modelMapper.map(importUmpires, GUmpires.class);
        gUmpires.setPlaygroundId(one.getId());
        return gUmpires;
    }


    //---------------------------------------------------------------------------------------------


    /**
     * 1.主裁判查询所有单元
     *
     * @param playGroundId
     * @return
     */
    @GetMapping("/getAllUnit/{playGroundId}")
    @PreAuthorize("@TAN.hasAuthority('primary')")
    public ResponseResult getAllUnit(@PathVariable Integer playGroundId) {
        List<GUnits> allUnitByMatch = gUnitsService.getAllUnitByMatch(playGroundId);
        if (!allUnitByMatch.isEmpty()) {
            return ResponseResult.okResult(allUnitByMatch);
        } else {
            return ResponseResult.errorResult(AppHttpCodeEnum.UNIT_NOT_EXIST);
        }
    }


    /**
     * 2.主裁判查询所有项目
     *
     * @param unitId
     * @return
     */
    @GetMapping("/getAllProject/{unitId}")
    @PreAuthorize("@TAN.hasAuthority('primary')")
    public ResponseResult getAllProjects(@PathVariable Integer unitId) {
        List<GProjects> allProjectByUinitId = projectsService.getAllProjectByUinitId(unitId);
        if (!allProjectByUinitId.isEmpty()) {
            return ResponseResult.okResult(allProjectByUinitId);
        } else {
            return ResponseResult.errorResult(400, "暂无项目");
        }
    }


    /**
     * 3.主裁判开始或结束项目状态
     * 0未开始,1进行中,2已结束
     *
     * @param status
     * @return
     */
    @GetMapping("/updateProjectStatus/{projectId}/{status}")
    @PreAuthorize("@TAN.hasAuthority('primary')")
    public ResponseResult updateProject(@PathVariable Integer projectId, @PathVariable Integer status) {
        GProjects byId = projectsService.getById(projectId);
        if (ObjectUtil.isNull(byId)) {
            return ResponseResult.errorResult(400, "项目不存在");
        } else {
            byId.setPStatus(status);
            boolean success = projectsService.updateById(byId);
            return success ? ResponseResult.okResult(200, "项目开启成功") : ResponseResult.errorResult(400, "项目开启失败");
        }
    }


    /**
     * 4.主裁判项目id获取参加队伍
     *
     * @param projectId
     * @return
     */
    @GetMapping("/getTeams/{projectId}/{current}/{size}")
    @PreAuthorize("@TAN.hasAuthority('primary')")
    public ResponseResult getTeamsByPId(
            @PathVariable Integer projectId,
            @PathVariable Integer current, // 当前页，默认为第1页
            @PathVariable Integer size // 每页的大小，默认为10
    ) {
        LambdaQueryWrapper<GTeamProject> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(GTeamProject::getPId, projectId);
        Page<GTeamProject> page = new Page<>(current, size); // 创建分页对象
        Page<GTeamProject> gTeamProjects = gTeamProjectService.page(page, queryWrapper); // 执行分页查询
        if (gTeamProjects.getRecords().isEmpty()) {
            return ResponseResult.errorResult(400, "暂无队伍参加");
        } else {
            return ResponseResult.okResult(gTeamProjects);
        }
    }


    /**
     * 5.主裁判修改队伍项目状态
     * 0:等待中,1:评分中,2:评分完成,3:已发布成绩,4:弃权,5:不予评分
     *
     * @param status
     * @return
     */
    @GetMapping("/updateTeamProjectStatus/{teamProjectId}/{status}")
    @PreAuthorize("@TAN.hasAuthority('primary')")
    public ResponseResult updateTeamProject(@PathVariable Integer teamProjectId, @PathVariable Integer status) {
        GTeamProject gTeamProject = gTeamProjectService.getById(teamProjectId);
        if (!ObjectUtil.isNull(gTeamProject)) {
            gTeamProject.setStatus(status);
            String message = this.getStatusMessage(status, gTeamProject.getName());
            boolean success = gTeamProjectService.updateById(gTeamProject);
            if (success) {
                return ResponseResult.okResult(200, message);
            }
            return ResponseResult.errorResult(400, "操作失败");
        }
        return ResponseResult.errorResult(400, "队伍不存在");
    }

    public String getStatusMessage(Integer status, String teamName) {
        String message = "";
        switch (status) {
            case 1:
                message = teamName + "开始评分";
                break;
            case 2:
                message = teamName + "评分完成";
                break;
            case 3:
                message = teamName + "设为弃权判定成功";
                break;
            case 4:
                message = teamName + "不予评分判定成功";
                break;
            default:
                break;
        }
        return message;
    }


    /**
     * 6.提交成绩
     *
     * @param updateTeamProjectList
     * @return
     */
    @PostMapping("/allScore")
    @PreAuthorize("@TAN.hasAuthority('primary')")
    public ResponseResult addAllScore(@RequestBody List<UpdateTeamProject> updateTeamProjectList) {
        if (updateTeamProjectList == null || updateTeamProjectList.isEmpty()) {
            return ResponseResult.errorResult(400, AppHttpCodeEnum.PARAM_REQUIRE);
        }
        List<GTeamProject> gTeamProjects = updateTeamProjectList.stream()
                .map(iGenerator::updateTeamProjectTeamProject)
                .collect(Collectors.toList());
        boolean success = gTeamProjectService.updateBatchById(gTeamProjects);
        if (success) {
            return ResponseResult.okResult(200, "提交成功");
        } else {
            return ResponseResult.errorResult(400, "提交失败，请检查数据");
        }
    }


    /**
     * 7.主裁判查询队伍项目得分
     *
     * @param projectId
     * @param teamProjectId
     * @param umpireId
     * @return
     */
    @GetMapping("/TeaProjectScore/{umpireId}/{teamProjectId}/{projectId}")
    @PreAuthorize("@TAN.hasAuthority('primary')")
    public ResponseResult getTeaProjectScore(@PathVariable Integer projectId, @PathVariable Integer teamProjectId, @PathVariable Integer umpireId) {
        LambdaQueryWrapper<GScoreUmpire> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(GScoreUmpire::getUmpireId, umpireId);
        queryWrapper.eq(GScoreUmpire::getProjectId, projectId);
        queryWrapper.eq(GScoreUmpire::getTeamProjectId, teamProjectId);
        List<GScoreUmpire> list = gScoreUmpireService.list(queryWrapper);
        if (list.isEmpty()) {
            return ResponseResult.errorResult(400, "暂无打分");
        }
        return ResponseResult.okResult(list);
    }


    //-----------------------------------------------------------------------------------------


    /**
     * 普通裁判获取打分历史
     *
     * @param teamProjectHistory
     * @return
     */
    @PostMapping("/getCurrent")
    public ResponseResult getTeamProjectHistory(@RequestBody TeamProjectHistory teamProjectHistory) {
        List<GScoreUmpireVo> scoreUmpireList = umpiresService.getHistory(teamProjectHistory);
        if (scoreUmpireList.isEmpty()) {
            return ResponseResult.errorResult(400, "暂无打分");
        } else {
            return ResponseResult.okResult(scoreUmpireList);
        }
    }


}