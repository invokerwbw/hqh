package com.dzwww.hqh.entity;

public enum FlagEnum {

    GRXMLB("1", "个人项目列表信息接口"), DWXMLB("2", "单位项目列表信息接口"), GRXMXX("3", "个人项目详细信息接口"), DWXMXX("4", "单位项目详细信息接口"), HWXMLB("5", "海外留学人员项目列表信息接口"), GNXMLB("6", "国内博士后项目列表信息接口");

    private String index;

    private String name;

    FlagEnum(String index, String name) {
        this.index = index;
        this.name = name;
    }

    public String getIndex() {
        return index;
    }

    public String getName() {
        return name;
    }
}
