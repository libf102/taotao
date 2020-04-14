package com.itszt.taotao.easyui.bean;

import java.io.Serializable;
import java.util.List;

public class EasyUIPageDatasBean<T> implements Serializable {

    private long total;    //返回前端的 数据总条数
    private List<T> rows;   //返回 当前页数据

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }
}
