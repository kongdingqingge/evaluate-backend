package com.lingxi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lingxi.model.po.GMatches;
import com.lingxi.model.vo.GunitVo;
import com.lingxi.model.vo.MatchVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 赛程表 Mapper 接口
 * </p>
 *
 * @author tan
 */
@Mapper
public interface GMatchesMapper extends BaseMapper<GMatches> {


    MatchVo getMatchAll();

    boolean deleteAll(Integer matchId);

    List<GunitVo> getUnitAndProjects(Integer playGroundId);

    Boolean dePlayGroundById(Integer playGroundId);

    void clearAll();

    void clearTeam();

    void clearUmpires();
}