package com.lingxi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lingxi.model.po.GProjects;

import java.util.List;

/**
 * <p>
 * 项目表 服务类
 * </p>
 *
 * @author tan
 * @since 2023-09-05
 */
public interface GProjectsService extends IService<GProjects> {

    List<GProjects> getAllProjectByUinitId(Integer unitId);
}
