package com.lingxi.exception;


import com.lingxi.enums.AppHttpCodeEnum;

public class CustomException extends RuntimeException {

    private AppHttpCodeEnum appHttpCodeEnum;

    public AppHttpCodeEnum getAppHttpCodeEnum() {
        return appHttpCodeEnum;
    }

    public CustomException(AppHttpCodeEnum appHttpCodeEnum) {
        super(appHttpCodeEnum.getMessage());
        this.appHttpCodeEnum = appHttpCodeEnum;
    }

    public void setAppHttpCodeEnum(AppHttpCodeEnum appHttpCodeEnum) {
        this.appHttpCodeEnum = appHttpCodeEnum;
    }
}
