package com.itszt.taotao.manager.controller;

import com.itszt.taotao.easyui.bean.EasyUIPageDatasBean;
import com.itszt.taotao.manager.service.inter.GoodsService;
import com.itszt.taotao.pojo.TbItem;
import freemarker.template.utility.NumberUtil;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
public class GoodsController {

    @Resource
    private GoodsService goodsService;

    //动态跳转
    @RequestMapping("{path}")
    public String goToItemList(@PathVariable(name = "path") String path){
        return path;
    }


    //这个是easyUI 内部的ajax 请求的 并返回数据      接收参数 : 当前页 每页显示数量
    @RequestMapping("/item/list")
    @ResponseBody      //false : 值 这个参数不是必须得 可以没有   defaultValue =默认值 如果前端没提交这个参数有个默认
                                //@RequestParam  还有一个作用就是 解决前后端键名不一致
    public EasyUIPageDatasBean<TbItem> getAllGoods(@RequestParam(required =false ,defaultValue="1") String page,
                                    @RequestParam(required = false,defaultValue = "20") String rows){
        //使用
        EasyUIPageDatasBean<TbItem> pageGoods = goodsService.getPageGoods(NumberUtils.toInt(page), NumberUtils.toInt(rows));
        return pageGoods;
    }


}
