package com.lingxi.enums;

public enum AppHttpCodeEnum {

    // 成功段固定为200
    SUCCESS(200, "操作成功"),
    // 登录段1~50
    NEED_LOGIN(1, "需要登录后操作"),
    LOGIN_PASSWORD_ERROR(2, "密码错误"),
    NO_LOGIN(3, "用户未登录"),
    // TOKEN50~100
    TOKEN_INVALID(50, "无效的TOKEN"),
    TOKEN_EXPIRE(51, "TOKEN已过期"),
    TOKEN_REQUIRE(52, "TOKEN是必须的"),
    // SIGN验签 100~120
    SIGN_INVALID(100, "无效的SIGN"),
    SIG_TIMEOUT(101, "SIGN已过期"),

    //数据库操作失败
    DATABASE_OPERTION(400, "数据库操作失败"),

    // 参数错误 500~1000
    PARAM_REQUIRE(500, "缺少参数,或为空"),

    PARAM_INVALID(501, "无效参数"),

    PARAM_IMAGE_FORMAT_ERROR(502, "图片格式有误"),
    NO_UNITS(503, "没有单元"),

    SERVER_ERROR(503, "服务器内部错误"),

    // 数据错误 1000~2000
    DATA_EXIST(1000, "数据已经存在"),

    REDIS_USER_DATA_NOT_EXIST(1001, "redis数据已过期"),

    DATA_NOT_EXIST(1002, "数据不存在"),

    // 数据错误 3000~3500
    NO_OPERATOR_AUTH(3000, "无权限操作"),

    NEED_ADMIND(3001, "需要管理员权限"),
    EMPIRES_DATA_NOT_EXIST(3002, "裁判不存在"),
    EMPIRES_LOCATION_ERROR(3022, "机位号设置失败"),
    USER_DATA_NOT_EXIST(3003, "用户不存在"),
    LOGIN_ACCOUNT_ERROR(3004, "账号或密码错误"), AUTH_DATA_ERROR(3005, "认证参数错误"),
    USER_TRSFOR_ERROR(3005, "无法转为用户对象"),

    GUmpires_TRSFOR_ERROR(3006, "无法转为裁判对象"),

    MATCH_NOT_EXIST(3007, "赛程不存在"),
    UNIT_NOT_EXIST(3008, "单元不存在"),
    PLAYGROUND_NOT_EXIST(3009, "场地不存在"),
    PROJECT_NOT_EXIST(3010, "项目不存在"),
    IMPORT_ERROR(3011, "导入失败"),

    FILE_DATE_MAX(4000, "上传失败，文件大小超过10M限制！"),
    FILE_NUMBER_MAX(4001, "删除失败,单次批量请求的文件数量不得超过1000");
    int code;
    String Message;

    AppHttpCodeEnum(int code, String message) {
        this.code = code;
        this.Message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return Message;
    }
}
