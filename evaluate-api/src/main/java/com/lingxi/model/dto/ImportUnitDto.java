package com.lingxi.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImportUnitDto {

    /**
     * 单元名称
     */
    @NotNull(message = "单元名称不为空")
    private String uName;


    /**
     * 项目数量
     */
    @NotNull(message = "项目数量不为空")
    private Integer projectCount;


    private List<ImportProjectsDto> importProjectsDtoList;

    public ImportUnitDto(String uName, Integer projectCount) {
        this.uName = uName;
        this.projectCount = projectCount;
    }
}
