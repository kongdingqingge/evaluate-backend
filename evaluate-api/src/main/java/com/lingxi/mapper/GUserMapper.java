package com.lingxi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lingxi.model.po.GUser;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author tan
 */
@Mapper
public interface GUserMapper extends BaseMapper<GUser> {

    GUser getOneByUsername(String username);

    GUser getOneByUesrId(Integer userId);
}