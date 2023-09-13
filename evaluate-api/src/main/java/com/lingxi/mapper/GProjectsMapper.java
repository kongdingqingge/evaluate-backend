package com.lingxi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lingxi.model.po.GProjects;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 项目表 Mapper 接口
 * </p>
 *
 * @author tan
 */
@Mapper
public interface GProjectsMapper extends BaseMapper<GProjects> {


    @Select("SELECT * FROM g_projects WHERE unit_id = #{unitId}")
    List<GProjects> getAllprojectByUintId(Integer unitId);
}