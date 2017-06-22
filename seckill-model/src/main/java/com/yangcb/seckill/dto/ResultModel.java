package com.yangcb.seckill.dto;

import java.util.List;

/**
 * pojo模型
 *
 * @author yangcb
 * @create 2017-06-05 14:38
 **/
public class  ResultModel<T> {

    //商品列表
    private List<T> list;
    //商品总数
    private Long recordCount;
    //总页数
    private Long pageCount;
    //当前页数
    private long curPage;


    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public Long getRecordCount() {
        return recordCount;
    }

    public void setRecordCount(Long recordCount) {
        this.recordCount = recordCount;
    }

    public Long getPageCount() {
        return pageCount;
    }

    public void setPageCount(Long pageCount) {
        this.pageCount = pageCount;
    }

    public long getCurPage() {
        return curPage;
    }

    public void setCurPage(long curPage) {
        this.curPage = curPage;
    }
}
