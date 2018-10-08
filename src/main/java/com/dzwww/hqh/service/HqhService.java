package com.dzwww.hqh.service;

import com.dzwww.hqh.entity.PageData;

import java.util.Map;

/**
 * 服务层
 */
public interface HqhService {

    /**
     * 项目列表信息
     *
     * @param type     接口类型
     * @param pageNum  当前页
     * @param pageSize 每页数据条数
     * @return
     */
    PageData listXm(String type, int pageNum, int pageSize);

    /**
     * 项目详细信息
     *
     * @param type 接口类型
     * @param key  项目主键
     * @return
     */
    Map<String, String> xmDetail(String type, String key);

}
