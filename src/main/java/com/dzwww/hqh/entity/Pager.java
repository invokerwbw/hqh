package com.dzwww.hqh.entity;

/**
 * 分页信息
 */
public class Pager {

    /**
     * 当前页
     */
    private int pageno;

    /**
     * 每页数据条数
     */
    private int pagesize;

    /**
     * 数据总记录数
     */
    private int records;

    public int getPageno() {
        return pageno;
    }

    public void setPageno(int pageno) {
        this.pageno = pageno;
    }

    public int getPagesize() {
        return pagesize;
    }

    public void setPagesize(int pagesize) {
        this.pagesize = pagesize;
    }

    public int getRecords() {
        return records;
    }

    public void setRecords(int records) {
        this.records = records;
    }
}
