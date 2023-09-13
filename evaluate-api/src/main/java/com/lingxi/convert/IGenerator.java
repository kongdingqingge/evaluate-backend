package com.lingxi.convert;

import com.lingxi.model.dto.ImportMatchDto;
import com.lingxi.model.dto.ImportPlayGroundDto;
import com.lingxi.model.dto.ImportProjectsDto;
import com.lingxi.model.dto.ImportUnitDto;
import com.lingxi.model.param.*;
import com.lingxi.model.po.*;
import com.lingxi.model.vo.UserVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IGenerator {


    //用户
    @Mapping(target = "uid", source = "id")
    UserVo toUserVo(GUser gUser);


    GMatches toGMatch(UpdateMatchParm updateMatch);

    GPlayground toPlayGround(UpdatePlayGroundPram updatePlayGroundPram);

    GProjects toProject(UpdateProjectPram updateProjectParm);

    GTeamProject toTeamProject(UpdateTeamProjectPram updateTeamProjectPram);

    GTeamProject toAddTeamProject(AddTeamProjectPram addTeamProjectPram);

    GMatches importMatchDtotoGMatch(ImportMatchDto importMatchDto);

    GPlayground playGroundDtotoPlayGround(ImportPlayGroundDto groundDto);

    ImportUnitDto importUnittoUnit(ImportUnitDto unitDto);

    GUnits importUnittoUnits(ImportUnitDto unitDto);

    GProjects ImportProjectstoProject(ImportProjectsDto eProjectDto);

    GTeamProject addScoretoTeamProject(AddScore item);

    GTeamProject updateTeamProjectTeamProject(UpdateTeamProject item);

    GTeams toTeam(UpdateTeam updateTeam);

    GUmpires updateUmpirePramto(UpdateUmpirePram updateUmpirePram);

    GProjects addProjectPramtoProject(AddProjectPram addProjectPram);
}
