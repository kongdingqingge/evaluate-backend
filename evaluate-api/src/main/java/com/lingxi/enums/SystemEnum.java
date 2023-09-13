package com.lingxi.enums;

public enum SystemEnum {
    /**
     * 队伍状态
     */
    T_WAITING(0, "等待中"),
    T_SCORING(2, "评分中"),
    T_T_SCORE_COMPLETED(3, "评分完成"),
    T_SCORE_PUBLISHED(4, "已发布成绩"),
    T_ABSTAINED(5, "弃权"),
    T_NOT_SCORED(6, "不予评分"),
    T_OVER_MATCHER(7, "结束比赛"),

    /**
     * 赛程状态
     */
    M_WAITING(0, "等待中"),
    M_ONGOING(1, "进行中"),
    M_OVER(2, "已结束"),

    /**
     * 单元状态
     */
    U_WAITING(0, "等待中"),
    U_ONGOING(1, "进行中"),
    U_OVER(2, "已结束"),

    /**
     * 项目组状态
     */
    G_WAITING(0, "等待中"),
    G_ONGOING(1, "进行中"),
    G_OVER(2, "已结束"),

    /**
     * 项目状态
     */
    P_WAITING(0, "等待中"),
    P_ONGOING(1, "进行中"),
    P_OVER(2, "已结束");

    private int value;
    private String description;

    SystemEnum(int value, String description) {
        this.value = value;
        this.description = description;
    }

    public int getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }
}
