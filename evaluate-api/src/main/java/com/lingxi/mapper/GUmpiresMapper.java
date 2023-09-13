package com.lingxi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lingxi.model.param.TeamProjectHistory;
import com.lingxi.model.po.GUmpires;
import com.lingxi.model.vo.GScoreUmpireVo;
import com.lingxi.model.vo.MarkGumpiresVo;
import com.lingxi.model.vo.PlayGroundUmpiresVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 裁判表 Mapper 接口
 * </p>
 *
 * @author tan
 */
@Mapper
public interface GUmpiresMapper extends BaseMapper<GUmpires> {

    GUmpires getUmpiresByAccount(String account);

    List<PlayGroundUmpiresVo> getUmpiresAll();


    GUmpires getUmpires(Integer id);

    int deleteUmpires(Integer id);

    int addUmpires(GUmpires gUmpires);

    List<GScoreUmpireVo> getOneAllScore(TeamProjectHistory teamProjectHistory);

    List<MarkGumpiresVo> getMarkUmpires(Integer playgroundId, Integer unitId, Integer projectId);

    Integer getMainUmpireId(Integer playgroundId);
}