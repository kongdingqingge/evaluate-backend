package com.lingxi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lingxi.model.po.GUnits;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 单元表 Mapper 接口
 * </p>
 *
 * @author tan
 */
@Mapper
public interface GUnitsMapper extends BaseMapper<GUnits> {

    /**
     * 修改单元
     *
     * @param gUnits
     * @return int
     * @author Cyou
     */
    int updateUnits(GUnits gUnits);

    /**
     * 添加单元
     *
     * @param gUnits
     * @return int
     * @author Cyou
     */
    int addUnits(GUnits gUnits);

    /**
     * 删除单元
     *
     * @param id
     * @return int
     * @author Cyou
     */
    int deleteUnitAndRelated(Integer id);


    @Select("SELECT * FROM g_units WHERE playground_id = #{playGroundId};")
    List<GUnits> getAllByMatchId(Integer playGroundId);
}