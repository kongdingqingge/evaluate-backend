package com.lingxi.model.vo;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GPlaygroundVo {

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 场地名称
     */
    private String playgroundName;

    /**
     * 赛程id
     */
    private Integer matchId;

    /**
     * 单元数量
     */
    private Integer unitCount;

    /**
     * 单元集合
     */
    private List<GunitVo> unitVoList;

}
