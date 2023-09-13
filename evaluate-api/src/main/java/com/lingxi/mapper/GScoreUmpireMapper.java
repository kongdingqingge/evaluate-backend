package com.lingxi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lingxi.model.po.GScoreUmpire;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 裁判打分表 Mapper 接口
 * </p>
 *
 * @author tan
 */
@Mapper
public interface GScoreUmpireMapper extends BaseMapper<GScoreUmpire> {

    Integer addScoreUmpire(GScoreUmpire gScoreUmpire1);

    GScoreUmpire getScoreUmpire(GScoreUmpire score);

    void updateScoreUmpire(GScoreUmpire gScoreUmpire);
}