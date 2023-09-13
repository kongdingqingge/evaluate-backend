package com.lingxi.controller;

import com.lingxi.service.GScoreUmpireService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 裁判打分表 前端控制器
 * </p>
 *
 * @author tan
 */
@Slf4j
@RestController
@RequestMapping("gScoreUmpire")
public class GScoreUmpireController {

    @Autowired
    private GScoreUmpireService  gScoreUmpireService;
}
