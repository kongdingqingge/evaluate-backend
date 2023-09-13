package com.lingxi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lingxi.model.po.GPlayground;

/**
 * <p>
 * 场地 服务类
 * </p>
 *
 * @author tan
 * @since 2023-09-05
 */
public interface GPlaygroundService extends IService<GPlayground> {

    Boolean delPlayGround(Integer playGroundId);
}
