package com.itszt.taotao.search.controller;

import com.itszt.taotao.search.inter.SearchItemResult;
import com.itszt.taotao.search.inter.SearchService;
import com.itszt.taotao.search.inter.SearchVO;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
public class SearchController {
    @Resource
    private SearchService searchService;

    @RequestMapping("doSearch.html")
    public String doSearch(SearchVO searchVO, Model model){
        SearchItemResult itemResult = searchService.search(searchVO);
        model.addAttribute("result",itemResult);
        return  "search";
    }
}
