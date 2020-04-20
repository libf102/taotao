package com.itszt.taotao.detail.controller;

import com.itszt.taotao.easyui.bean.ItemPicUtil;
import com.itszt.taotao.manager.service.inter.GoodsService;
import com.itszt.taotao.pojo.TbItem;
import com.itszt.taotao.pojo.TbItemDesc;
import org.apache.http.HttpResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ItemController {
    @Resource
    private GoodsService goodsService;
    @Resource
    private FreemarkerCacheUtil freemarkerCacheUtil;

    @RequestMapping("/item/{id}")
    public void showItemDetail(@PathVariable(name="id") long goodsId , Model model, HttpServletResponse response){
//        TbItem tbItem = goodsService.getTbItemById(goodsId);
//        List<String> images = new ArrayList<>();
//        String[] split = tbItem.getImage().split(",");
//        for (String s : split) {
//            images.add(ItemPicUtil.genFullPath(s));
//        }
//        TbItemDesc tbItemDescById = goodsService.getTbItemDescById(goodsId);
//        model.addAttribute("item",tbItem);
//        model.addAttribute("itemDesc",tbItemDescById);
//        model.addAttribute("itemImages",images);
//        return "item";

        try {
            response.setCharacterEncoding("UTF-8");
            Writer out = response.getWriter();
            boolean b = freemarkerCacheUtil.processFtlCache("item.ftl", out, goodsId + "");
            if (!b) {
                TbItem tbItem = goodsService.getTbItemById(goodsId);
                List<String> images = new ArrayList<>();
                String[] split = tbItem.getImage().split(",");
                for (String s : split) {
                    images.add(ItemPicUtil.genFullPath(s));
                }
                TbItemDesc tbItemDescById = goodsService.getTbItemDescById(goodsId);
                freemarkerCacheUtil.addAttribute("item",tbItem);
                freemarkerCacheUtil.addAttribute("itemDesc",tbItemDescById);
                freemarkerCacheUtil.addAttribute("itemImages",images);
                freemarkerCacheUtil.processFtl("item.ftl",out,goodsId+"");

            }

        }  catch (Exception e) {
            e.printStackTrace();
        }


    }
}
