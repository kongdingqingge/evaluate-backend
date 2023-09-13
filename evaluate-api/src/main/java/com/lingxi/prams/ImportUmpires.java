package com.lingxi.prams;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImportUmpires {


    @Excel(name = "场地名称", isImportField = "true")
    @NotNull(message = "场地名称不为空")
    private String playgroundName;


    @Excel(name = "账号", isImportField = "true")
    @NotNull(message = "账号不为空")
    private String account;


    @Excel(name = "姓名", isImportField = "true")
    @NotNull(message = "姓名不为空")
    private String name;


    @Excel(name = "类型", isImportField = "true")
    @NotNull(message = "类型不为空")
    private String type_String;

    private Integer type;


}
