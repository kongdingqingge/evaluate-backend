package com.lingxi.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImportPlayGroundDto {

    /**
     * 场地名称
     */
    @NotNull(message = "场地名称不为空")
    private String playgroundName;


    /**
     * 单元数量
     */
    @NotNull(message = "单元数量不为空")
    private Integer unitCount;


    private List<ImportUnitDto> importUnitDtoList;


    public ImportPlayGroundDto(String playgroundName, Integer unitCount) {
        this.playgroundName = playgroundName;
        this.unitCount = unitCount;
    }
}
