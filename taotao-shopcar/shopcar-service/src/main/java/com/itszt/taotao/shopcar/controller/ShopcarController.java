package com.itszt.taotao.shopcar.controller;

import com.itszt.taotao.easyui.bean.ItemPicUtil;
import com.itszt.taotao.pojo.TbShopcar;
import com.itszt.taotao.shopcar.inter.ShopcarService;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

@Controller
public class ShopcarController {
    @Resource
    private ShopcarService shopcarService;

    @RequestMapping("/cart/cartlist")
    public  String showShopcar(Model model){
        Long userId = NumberUtils.createLong("123456789");
        List<TbShopcar> shopcar = shopcarService.getShopcar(userId);
        //显示购物车总金额
        int totalPrice =0;
        for (TbShopcar tbShopcar : shopcar) {
            Integer price = tbShopcar.getPrice();
            totalPrice+=price;
            tbShopcar.setItemImg(ItemPicUtil.genFullPath(tbShopcar.getItemImg()));

        }
        model.addAttribute("tbShopcars",shopcar);
        model.addAttribute("shopCarTotal",totalPrice);
        return "cart";
    }
}
