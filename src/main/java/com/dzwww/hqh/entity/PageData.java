package com.dzwww.hqh.entity;

import java.util.List;
import java.util.Map;

/**
 * 带有分页信息的数据集
 */
public class PageData {

    /**
     * 分页信息
     */
    private Pager pager;

    /**
     * 数据列表
     */
    private List<Map<String, String>> rows;

    public Pager getPager() {
        return pager;
    }

    public void setPager(Pager pager) {
        this.pager = pager;
    }

    public List<Map<String, String>> getRows() {
        return rows;
    }

    public void setRows(List<Map<String, String>> rows) {
        this.rows = rows;
    }
}
