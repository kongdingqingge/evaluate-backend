package com.lingxi.convert;

import com.lingxi.model.dto.ImportMatchDto;
import com.lingxi.model.dto.ImportPlayGroundDto;
import com.lingxi.model.dto.ImportProjectsDto;
import com.lingxi.model.dto.ImportUnitDto;
import com.lingxi.model.param.AddProjectPram;
import com.lingxi.model.param.AddScore;
import com.lingxi.model.param.AddTeamProjectPram;
import com.lingxi.model.param.UpdateMatchParm;
import com.lingxi.model.param.UpdatePlayGroundPram;
import com.lingxi.model.param.UpdateProjectPram;
import com.lingxi.model.param.UpdateTeam;
import com.lingxi.model.param.UpdateTeamProject;
import com.lingxi.model.param.UpdateTeamProjectPram;
import com.lingxi.model.param.UpdateUmpirePram;
import com.lingxi.model.po.GMatches;
import com.lingxi.model.po.GPlayground;
import com.lingxi.model.po.GProjects;
import com.lingxi.model.po.GTeamProject;
import com.lingxi.model.po.GTeams;
import com.lingxi.model.po.GUmpires;
import com.lingxi.model.po.GUnits;
import com.lingxi.model.po.GUser;
import com.lingxi.model.vo.UserVo;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-09-13T16:52:54+0800",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 11.0.6 (Oracle Corporation)"
)
@Component
public class IGeneratorImpl implements IGenerator {

    @Override
    public UserVo toUserVo(GUser gUser) {
        if ( gUser == null ) {
            return null;
        }

        UserVo userVo = new UserVo();

        userVo.setUid( gUser.getId() );
        userVo.setUsername( gUser.getUsername() );
        userVo.setPassword( gUser.getPassword() );

        return userVo;
    }

    @Override
    public GMatches toGMatch(UpdateMatchParm updateMatch) {
        if ( updateMatch == null ) {
            return null;
        }

        GMatches gMatches = new GMatches();

        gMatches.setId( updateMatch.getId() );
        gMatches.setMName( updateMatch.getMName() );
        gMatches.setMStatus( updateMatch.getMStatus() );
        gMatches.setPlaygroundCount( updateMatch.getPlaygroundCount() );

        return gMatches;
    }

    @Override
    public GPlayground toPlayGround(UpdatePlayGroundPram updatePlayGroundPram) {
        if ( updatePlayGroundPram == null ) {
            return null;
        }

        GPlayground gPlayground = new GPlayground();

        gPlayground.setId( updatePlayGroundPram.getId() );
        gPlayground.setPlaygroundName( updatePlayGroundPram.getPlaygroundName() );
        gPlayground.setMatchId( updatePlayGroundPram.getMatchId() );
        gPlayground.setUnitCount( updatePlayGroundPram.getUnitCount() );

        return gPlayground;
    }

    @Override
    public GProjects toProject(UpdateProjectPram updateProjectParm) {
        if ( updateProjectParm == null ) {
            return null;
        }

        GProjects gProjects = new GProjects();

        gProjects.setId( updateProjectParm.getId() );
        gProjects.setPName( updateProjectParm.getPName() );
        gProjects.setUnitId( updateProjectParm.getUnitId() );
        gProjects.setPStatus( updateProjectParm.getPStatus() );

        return gProjects;
    }

    @Override
    public GTeamProject toTeamProject(UpdateTeamProjectPram updateTeamProjectPram) {
        if ( updateTeamProjectPram == null ) {
            return null;
        }

        GTeamProject gTeamProject = new GTeamProject();

        gTeamProject.setId( updateTeamProjectPram.getId() );
        gTeamProject.setProjectName( updateTeamProjectPram.getProjectName() );

        return gTeamProject;
    }

    @Override
    public GTeamProject toAddTeamProject(AddTeamProjectPram addTeamProjectPram) {
        if ( addTeamProjectPram == null ) {
            return null;
        }

        GTeamProject gTeamProject = new GTeamProject();

        gTeamProject.setTeamId( addTeamProjectPram.getTeamId() );
        gTeamProject.setName( addTeamProjectPram.getName() );
        gTeamProject.setProjectName( addTeamProjectPram.getProjectName() );

        return gTeamProject;
    }

    @Override
    public GMatches importMatchDtotoGMatch(ImportMatchDto importMatchDto) {
        if ( importMatchDto == null ) {
            return null;
        }

        GMatches gMatches = new GMatches();

        gMatches.setMName( importMatchDto.getMName() );
        gMatches.setPlaygroundCount( importMatchDto.getPlaygroundCount() );

        return gMatches;
    }

    @Override
    public GPlayground playGroundDtotoPlayGround(ImportPlayGroundDto groundDto) {
        if ( groundDto == null ) {
            return null;
        }

        GPlayground gPlayground = new GPlayground();

        gPlayground.setPlaygroundName( groundDto.getPlaygroundName() );
        gPlayground.setUnitCount( groundDto.getUnitCount() );

        return gPlayground;
    }

    @Override
    public ImportUnitDto importUnittoUnit(ImportUnitDto unitDto) {
        if ( unitDto == null ) {
            return null;
        }

        ImportUnitDto importUnitDto = new ImportUnitDto();

        importUnitDto.setUName( unitDto.getUName() );
        importUnitDto.setProjectCount( unitDto.getProjectCount() );
        List<ImportProjectsDto> list = unitDto.getImportProjectsDtoList();
        if ( list != null ) {
            importUnitDto.setImportProjectsDtoList( new ArrayList<ImportProjectsDto>( list ) );
        }

        return importUnitDto;
    }

    @Override
    public GUnits importUnittoUnits(ImportUnitDto unitDto) {
        if ( unitDto == null ) {
            return null;
        }

        GUnits gUnits = new GUnits();

        gUnits.setUName( unitDto.getUName() );
        gUnits.setProjectCount( unitDto.getProjectCount() );

        return gUnits;
    }

    @Override
    public GProjects ImportProjectstoProject(ImportProjectsDto eProjectDto) {
        if ( eProjectDto == null ) {
            return null;
        }

        GProjects gProjects = new GProjects();

        gProjects.setPName( eProjectDto.getPName() );

        return gProjects;
    }

    @Override
    public GTeamProject addScoretoTeamProject(AddScore item) {
        if ( item == null ) {
            return null;
        }

        GTeamProject gTeamProject = new GTeamProject();

        gTeamProject.setName( item.getName() );

        return gTeamProject;
    }

    @Override
    public GTeamProject updateTeamProjectTeamProject(UpdateTeamProject item) {
        if ( item == null ) {
            return null;
        }

        GTeamProject gTeamProject = new GTeamProject();

        gTeamProject.setId( item.getId() );
        gTeamProject.setPId( item.getPId() );
        gTeamProject.setTime( item.getTime() );
        gTeamProject.setStatus( item.getStatus() );
        gTeamProject.setTotalScore( item.getTotalScore() );

        return gTeamProject;
    }

    @Override
    public GTeams toTeam(UpdateTeam updateTeam) {
        if ( updateTeam == null ) {
            return null;
        }

        GTeams gTeams = new GTeams();

        gTeams.setId( updateTeam.getId() );
        gTeams.setName( updateTeam.getName() );
        gTeams.setMembers( updateTeam.getMembers() );

        return gTeams;
    }

    @Override
    public GUmpires updateUmpirePramto(UpdateUmpirePram updateUmpirePram) {
        if ( updateUmpirePram == null ) {
            return null;
        }

        GUmpires gUmpires = new GUmpires();

        gUmpires.setId( updateUmpirePram.getId() );
        gUmpires.setAccount( updateUmpirePram.getAccount() );
        gUmpires.setName( updateUmpirePram.getName() );
        gUmpires.setType( updateUmpirePram.getType() );

        return gUmpires;
    }

    @Override
    public GProjects addProjectPramtoProject(AddProjectPram addProjectPram) {
        if ( addProjectPram == null ) {
            return null;
        }

        GProjects gProjects = new GProjects();

        gProjects.setPName( addProjectPram.getPName() );
        gProjects.setUnitId( addProjectPram.getUnitId() );
        gProjects.setPStatus( addProjectPram.getPStatus() );

        return gProjects;
    }
}
