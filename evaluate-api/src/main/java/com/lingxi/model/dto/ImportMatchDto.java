package com.lingxi.model.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImportMatchDto {

    /**
     * 赛程名称
     */
    @NotNull(message = "赛程名称不为空")
    private String mName;

    /**
     * 场地数量
     */
    @NotNull(message = "场地数量不为空")
    private Integer playgroundCount;


    private List<ImportPlayGroundDto> importPlayGroundDto;


    public ImportMatchDto(String mName, Integer playgroundCount) {
        this.mName = mName;
        this.playgroundCount = playgroundCount;
    }
}
