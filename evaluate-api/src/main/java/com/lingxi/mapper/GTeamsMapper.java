package com.lingxi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lingxi.model.po.GTeams;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 队伍表 Mapper 接口
 * </p>
 *
 * @author tan
 */
@Mapper
public interface GTeamsMapper extends BaseMapper<GTeams> {

    /**
     * 添加队伍
     *
     * @param gTeams
     * @return int
     * @author Cyou
     */
    int addTeams(GTeams gTeams);

    /**
     * 修改队伍
     *
     * @param gTeams
     * @return int
     * @author Cyou
     */
    int updateTeams(GTeams gTeams);

    /**
     * 删除队伍
     *
     * @param id
     * @return int
     * @author Cyou
     */
    int deleteTeams(Integer id);

}