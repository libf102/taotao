package com.itszt.taotao.manager.controller;

import com.itszt.taotao.manager.service.inter.GoodsService;
import com.itszt.taotao.pojo.TbItem;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("goods")
public class GoodsController {

    @Resource
    private GoodsService goodsService;

    @RequestMapping("getAllGoods")
    @ResponseBody
    public List<TbItem> getAllGoods(){
        List<TbItem> allGoods = goodsService.getAllGoods();

        return allGoods;
    }
}
