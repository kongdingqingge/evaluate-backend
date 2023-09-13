package com.lingxi.model.excel;

import com.lingxi.model.vo.UserVo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginReturn {

    /**
     * 登入系统的类型
     */
    private String SysteamType;

    private UserVo userVo;

    private com.lingxi.model.po.GUmpires GUmpires;

    /**
     * 登入信息
     */
    private Map<String, Object> messageMap;


    public LoginReturn(String systeamType, UserVo userVo, Map<String, Object> messageMap) {
        SysteamType = systeamType;
        this.userVo = userVo;
        this.messageMap = messageMap;
    }

    public LoginReturn(String systeamType, com.lingxi.model.po.GUmpires GUmpires, Map<String, Object> messageMap) {
        SysteamType = systeamType;
        this.GUmpires = GUmpires;
        this.messageMap = messageMap;
    }
}
