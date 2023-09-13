package com.lingxi.model.vo;

import com.lingxi.model.po.GTeamProject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeamProjectVo {


    /**
     * id
     */
    private Integer id;

    /**
     * 项目id
     */
    private Integer projectId;

    /**
     * 名称
     */
    private String name;

    /**
     * 队伍编号
     */
    private String number;

    /**
     * 运动员
     */
    private String members;

    /**
     * 队伍参加项目
     */
    private List<GTeamProject> teamProjectList;

}
