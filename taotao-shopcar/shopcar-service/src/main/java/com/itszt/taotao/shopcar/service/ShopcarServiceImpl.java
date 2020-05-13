package com.itszt.taotao.shopcar.service;

import com.alibaba.fastjson.JSONObject;
import com.itszt.taotao.pojo.TbShopcar;
import com.itszt.taotao.shopcar.dao.ShopcarDao;
import com.itszt.taotao.shopcar.inter.ShopcarService;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ShopcarServiceImpl implements ShopcarService {
    @Resource
    private KafkaTemplate kafkaTemplate;
    @Resource
    private ShopcarDao shopcarDao;
    @Override    //添加购物城功能      将参数传到消息队列
    public boolean itemAddShopcar(long itemId, long userId) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("itemId",itemId);
        jsonObject.put("userId",userId);
        String msg =jsonObject.toJSONString();
        try {
            kafkaTemplate.send("ShopCarTopic",msg);  //消息队列也可以存其他的格式
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public List<TbShopcar> getShopcar(long userId) {
        List<TbShopcar> tbShopcars = shopcarDao.queryUserShopcar(userId);
        return tbShopcars;
    }
}
