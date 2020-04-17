package com.itszt.taotao.search.inter;

public class SearchVO {
    private String keyword;
    private Integer page=1;
    private String sort="price";
    private  boolean desc=true;
    private Integer cid=560;
    private Integer lowerPrice=100000;
    private Integer higerPrice=200000;


    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public boolean isDesc() {
        return desc;
    }

    public void setDesc(boolean desc) {
        this.desc = desc;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public Integer getLowerPrice() {
        return lowerPrice;
    }

    public void setLowerPrice(Integer lowerPrice) {
        this.lowerPrice = lowerPrice;
    }

    public Integer getHigerPrice() {
        return higerPrice;
    }

    public void setHigerPrice(Integer higerPrice) {
        this.higerPrice = higerPrice;
    }
}
