package com.lingxi.model.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.lingxi.model.po.GProjects;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GunitVo {


    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 单元名称
     */
    private String uName;

    /**
     * 场地id
     */
    private Integer playgroundId;

    /**
     * 项目数量
     */
    private Integer projectCount;

    /**
     * 项目集合
     */
    private List<GProjects> projectsList;

}
