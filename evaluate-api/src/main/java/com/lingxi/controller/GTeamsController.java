package com.lingxi.controller;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lingxi.convert.IGenerator;
import com.lingxi.enums.AppHttpCodeEnum;
import com.lingxi.exception.CustomException;
import com.lingxi.model.param.UpdateTeam;
import com.lingxi.model.po.GProjects;
import com.lingxi.model.po.GTeamProject;
import com.lingxi.model.po.GTeams;
import com.lingxi.result.ResponseResult;
import com.lingxi.service.GMatchesService;
import com.lingxi.service.GProjectsService;
import com.lingxi.service.GTeamProjectService;
import com.lingxi.service.GTeamsService;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

/**
 * <p>
 * 队伍表 前端控制器
 * </p>
 *
 * @author tan
 */
@Slf4j
@RestController
@RequestMapping("gTeams")
public class GTeamsController {

    @Autowired
    private GTeamsService gTeamsService;

    @Autowired
    private GProjectsService gProjectsService;

    @Autowired
    private GTeamProjectService gTeamProjectService;

    @Autowired
    private IGenerator iGenerator;

    @Autowired
    private GMatchesService gMatchesService;

    private static String getStringCellValue(Cell cell) {
        if (cell == null || cell.getCellType() == CellType.BLANK) {
            return "";
        }
        return cell.getCellType() == CellType.STRING ? cell.getStringCellValue() : "";
    }

    /**
     * 查询单个队伍
     *
     * @param id
     * @return com.lingxi.result.ResponseResult
     * @author Cyou
     */
    @GetMapping("/getOne/{id}")
    @PreAuthorize("@TAN.hasAuthority('admin')")
    public ResponseResult getTeamsOne(@PathVariable("id") Integer id) {
        GTeams team = gTeamsService.getById(id);
        return new ResponseResult(AppHttpCodeEnum.SUCCESS.getCode(), team);
    }


    /**
     * 获取所有队伍
     *
     * @return
     */
    @GetMapping("/getAll")
    @PreAuthorize("@TAN.hasAuthority('admin')")
    public ResponseResult getTeams() {
        List<GTeams> gTeams = gTeamsService.list();
        if (gTeams.isEmpty()) {
            return ResponseResult.okResult(0);
        } else {
            return ResponseResult.okResult(gTeams.size());
        }
    }


    /**
     * 查询全部队伍
     *
     * @return com.lingxi.result.ResponseResult
     * @author Cyou
     */
    @GetMapping("/getAll/{pageNum}/{pageSize}")
    @PreAuthorize("@TAN.hasAuthority('admin')")
    public ResponseResult getTeamsAll(@PathVariable("pageNum") Integer pageNum, @PathVariable("pageSize") Integer pageSize) {
        List<GTeams> teams = gTeamsService.getTeamsAll(pageNum, pageSize);
        if (!teams.isEmpty()) {
            return ResponseResult.okResult(teams);
        } else {
            return ResponseResult.errorResult(400, "暂无队伍");
        }
    }


    /**
     * 添加队伍
     *
     * @param gTeams
     * @return com.lingxi.result.ResponseResult
     * @author Cyou
     */
    @PostMapping("/addTeams")
    @PreAuthorize("@TAN.hasAuthority('admin')")
    public ResponseResult addTeams(@RequestBody @Valid GTeams gTeams) {
        return gTeamsService.addTeams(gTeams);
    }

    /**
     * 修改队伍
     *
     * @param updateTeam
     * @return com.lingxi.result.ResponseResult
     * @author Cyou
     */
    @PostMapping("/updateTeams")
    @PreAuthorize("@TAN.hasAuthority('admin')")
    public ResponseResult updateTeams(@RequestBody @Valid UpdateTeam updateTeam) {
        GTeams teams = iGenerator.toTeam(updateTeam);
        boolean success = gTeamsService.updateById(teams);
        if (success) {
            return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
        }
        return ResponseResult.errorResult(400, "修改失败");
    }

    /**
     * 删除队伍
     *
     * @param teamId
     * @return com.lingxi.result.ResponseResult
     * @author Cyou
     */
    @GetMapping("/deleteTeams/{teamId}")
    @PreAuthorize("@TAN.hasAuthority('admin')")
    public ResponseResult deleteTeams(@PathVariable Integer teamId) {
        GTeams gTeams = gTeamsService.getById(teamId);
        if (ObjectUtil.isNull(gTeams)) {
            return ResponseResult.errorResult(400, "队伍不存在,无法删除");
        }
        return gTeamsService.deleteTeams(teamId);
    }


    // 判断行是否为空白行
    private boolean isRowEmpty(Row row) {
        if (row == null) {
            return true;
        }
        for (int cellNum = row.getFirstCellNum(); cellNum < row.getLastCellNum(); cellNum++) {
            Cell cell = row.getCell(cellNum);
            if (cell != null && cell.getCellType() != CellType.BLANK) {
                return false;
            }
        }
        return true;
    }


    /**
     * 导入队伍,项目
     *
     * @param file
     * @return
     */
    @PostMapping("/importTeam")
    @PreAuthorize("@TAN.hasAuthority('admin')")
    @Transactional(rollbackFor = Exception.class) // 引入事务处理
    public ResponseResult importTeamProject(@RequestParam("file") MultipartFile file) {
        List<GTeams> list = gTeamsService.list();
        boolean success = true;
        if (!list.isEmpty()) {
            success = gTeamsService.deleteTeamAndProject();
        }
        if (success) {
            try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {
                // 遍历每个 sheet
                for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
                    Sheet sheet = workbook.getSheetAt(i);
                    LambdaQueryWrapper<GProjects> wrapper = new LambdaQueryWrapper<>();
                    wrapper.eq(GProjects::getPName, sheet.getSheetName());
                    GProjects projects = gProjectsService.getOne(wrapper);
                    // 遍历每行
                    for (int j = 1; j <= sheet.getLastRowNum(); j++) {
                        Row row = sheet.getRow(j);
                        // 判断是否为空白行
                        if (isRowEmpty(row)) {
                            break; // 结束当前 sheet 的读取
                        }
                        String sportsman = getStringCellValue(row.getCell(0));
                        String name = getStringCellValue(row.getCell(1));
                        String content = getStringCellValue(row.getCell(2));
                        GTeams gTeams = new GTeams(name, sportsman);
                        LambdaQueryWrapper<GTeams> gTeamsLambdaQueryWrapper = new LambdaQueryWrapper<>();
                        gTeamsLambdaQueryWrapper.eq(GTeams::getName, name);
                        GTeams one = gTeamsService.getOne(gTeamsLambdaQueryWrapper);
                        //队伍不存在
                        if (ObjectUtil.isNull(one)) {
                            Boolean teamSaved = gTeamsService.save(gTeams);
                            if (teamSaved) {
                                GTeamProject gTeamProject = new GTeamProject(gTeams.getId(), projects.getId(), gTeams.getName(), projects.getPName(), content);
                                boolean projectSaved = gTeamProjectService.save(gTeamProject);
                                if (!projectSaved) {
                                    throw new CustomException(AppHttpCodeEnum.DATABASE_OPERTION);
                                }
                            } else {
                                throw new CustomException(AppHttpCodeEnum.DATABASE_OPERTION);
                            }
                        } else {
                            //队伍存在
                            GTeamProject gTeamProject = new GTeamProject(one.getId(), projects.getId(), gTeams.getName(), projects.getPName(), content);
                            boolean projectSaved = gTeamProjectService.save(gTeamProject);
                            if (!projectSaved) {
                                throw new CustomException(AppHttpCodeEnum.DATABASE_OPERTION);
                            }
                        }
                    }
                }
                return ResponseResult.okResult(200, "数据导入成功");
            } catch (IOException e) {
                e.printStackTrace();
                throw new CustomException(AppHttpCodeEnum.IMPORT_ERROR);
            }
        }
        return ResponseResult.errorResult(AppHttpCodeEnum.SERVER_ERROR);
    }


}