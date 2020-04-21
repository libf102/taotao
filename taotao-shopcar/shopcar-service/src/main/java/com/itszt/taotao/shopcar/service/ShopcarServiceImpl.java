package com.itszt.taotao.shopcar.service;

import com.alibaba.fastjson.JSONObject;
import com.itszt.taotao.shopcar.inter.ShopcarService;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
@Service
public class ShopcarServiceImpl implements ShopcarService {
    @Resource
    private KafkaTemplate kafkaTemplate;
    @Override
    public boolean itemAddShopcar(long itemId, long userId) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("itemId",itemId);
        jsonObject.put("userId",userId);
        String msg =jsonObject.toJSONString();
        try {
            kafkaTemplate.send("ShopCarTopic",  msg);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }
}
