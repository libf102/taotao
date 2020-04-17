package com.itszt.taotao.search.dao;

import com.itszt.taotao.pojo.TbItem;

import java.util.List;
import java.util.Map;

public class SearchDTO {
    private List<TbItem> tbItems;   //根据条件 查询的 List 结果
    Map<String, Map<String, List<String>>> highlighting;   //高亮处理
    private Long numFound;        //查询结果的 总条数


    public Long getNumFound() {
        return numFound;
    }

    public void setNumFound(Long numFound) {
        this.numFound = numFound;
    }

    public List<TbItem> getTbItems() {
        return tbItems;
    }

    public void setTbItems(List<TbItem> tbItems) {
        this.tbItems = tbItems;
    }

    public Map<String, Map<String, List<String>>> getHighlighting() {
        return highlighting;
    }

    public void setHighlighting(Map<String, Map<String, List<String>>> highlighting) {
        this.highlighting = highlighting;
    }
}
