package com.lingxi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lingxi.model.po.GTeamProject;
import com.lingxi.model.vo.TeamProjectVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 队伍项目表 Mapper 接口
 * </p>
 *
 * @author tan
 */
@Mapper
public interface GTeamProjectMapper extends BaseMapper<GTeamProject> {

    List<TeamProjectVo> getTeamProjects();


}

