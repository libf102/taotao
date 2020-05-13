package com.itszt.taotao.shopcar;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.itszt.taotao.manager.service.inter.GoodsService;
import com.itszt.taotao.pojo.TbItem;
import com.itszt.taotao.pojo.TbShopcar;

import com.itszt.taotao.shopcar.dao.ShopcarDao;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.listener.MessageListener;

import javax.annotation.Resource;
import java.util.Date;

public class MyConsumer implements MessageListener<String,String> {
    @Resource
    private ShopcarDao shopcarDao;
    @Resource
    private GoodsService goodsService;
    @Override
    public void onMessage(ConsumerRecord<String, String> data) {

        String value = data.value();

        JSONObject jsonObject = JSON.parseObject(value);

        Long userId = NumberUtils.createLong(jsonObject.get("userId").toString());
        Long itemId = NumberUtils.createLong(jsonObject.get("itemId").toString());

        TbShopcar tbShopcar = shopcarDao.queryShopCarItem(itemId,userId);
        if (tbShopcar==null) {
            //之前购物未添加过该商品
            tbShopcar=new TbShopcar();

            tbShopcar.setAddDate(new Date());
            tbShopcar.setItemId(itemId);
            tbShopcar.setUserId((int)(long)userId);
            tbShopcar.setNum(1);
            tbShopcar.setTag(System.currentTimeMillis());

            TbItem tbItem = goodsService.getTbItemById(itemId);
            tbShopcar.setItemImg(tbItem.getImage().split(",")[0]);
            int price=(int)(long)tbItem.getPrice();
            tbShopcar.setPrice(price);
            tbShopcar.setTotalPrice(price);
            tbShopcar.setItemTitle(tbItem.getTitle());

            shopcarDao.insertShopCarItem(tbShopcar);

        }else {
            //添加过
            Integer oldNum = tbShopcar.getNum();
            Integer newNum=oldNum+1;

            Integer totalPrice=tbShopcar.getPrice()*newNum;

            shopcarDao.updateShopCarItem(tbShopcar.getId(), newNum,System.currentTimeMillis(),totalPrice );

        }

    }
}
