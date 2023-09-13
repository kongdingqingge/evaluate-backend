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
public class MatchVo {


    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 赛程名称
     */
    private String mName;

    /**
     * 赛程状态;0等待中,1进行中,2已结束
     */
    private Integer mStatus;

    /**
     * 场地数量
     */
    private Integer playgroundCount;

    /**
     * 场地集合
     */
    private List<GPlaygroundVo> playgroundList;

}
