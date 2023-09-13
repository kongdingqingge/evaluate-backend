package com.lingxi.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImportProjectsDto {

    /**
     * 项目名
     */
    @NotNull(message = "项目名不为空")
    private String pName;

//    public ImportProjectsDto(String pName) {
//        this.pName = pName;
//    }
}
