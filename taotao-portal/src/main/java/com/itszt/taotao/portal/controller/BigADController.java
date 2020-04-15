package com.itszt.taotao.portal.controller;

import com.itszt.taotao.manager.service.inter.ContentService;
import com.itszt.taotao.pojo.TbContent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RestController
public class BigADController {
    @Resource
    private ContentService contentService;
    @Value("${width}")
    private String width;
    @Value("${height}")
    private String height;
    @RequestMapping("/getBigADS.json")   //根据tb_content对象(后台界面 根据类型添加的广告对象)    返回BigADOV(页面可以展示的广告对象)
    public List<BigADOV> getBigADS(){
        List<TbContent> content = contentService.getContentByCategoryId();
        List<BigADOV> bigADOVS = BigADOV.convertBigADOV(content,Integer.parseInt(height),Integer.parseInt(width));
        return bigADOVS;
    }

}
