package com.lingxi.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.lingxi.model.dto.ImportMatchDto;
import com.lingxi.model.po.GMatches;
import com.lingxi.model.vo.GunitVo;
import com.lingxi.model.vo.MatchVo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 赛程表 服务类
 * </p>
 *
 * @author tan
 * @since 2023-07-04
 */
public interface GMatchesService extends IService<GMatches> {
    MatchVo getAllMatches();

    boolean deleteMatch(Integer matchId);

    List<GunitVo> getUnitsByGId(Integer matchId);


    /**
     * 表格导入赛程
     */
    ImportMatchDto importMatch(MultipartFile file);

    void clearAll();

    void clearTeam();

    void clearUmpires();


}
