package com.lingxi.model.vo;

import com.lingxi.model.po.GUmpires;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MarkGumpiresVo {

    List<GUmpires> gUmpires;

}