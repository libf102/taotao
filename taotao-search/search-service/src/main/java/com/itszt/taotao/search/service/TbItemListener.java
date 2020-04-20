package com.itszt.taotao.search.service;

import com.alibaba.fastjson.JSON;
import com.itszt.taotao.easyui.bean.GoodsMsg;
import com.itszt.taotao.pojo.TbItem;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.kafka.listener.MessageListener;

import javax.annotation.Resource;
import java.io.IOException;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TbItemListener implements MessageListener<String,String> {
    @Resource
    private HttpSolrServer solrServer;   //TbItemListener 被IOC托管 可以使用spring 注解
    @Override
    public void onMessage(ConsumerRecord<String, String> data) {
        String value = data.value();   //消息的 值  json格式
        GoodsMsg goodsMsg = JSON.parseObject(value, GoodsMsg.class);
        String opt = goodsMsg.getOpt();
        switch (opt){
            case "add" :
                SolrInputDocument document = new SolrInputDocument();   //相当于solr 的一条 json
                TbItem tbItemNew = goodsMsg.getTbItemNew();
                try {
                    Field[] fields = TbItem.class.getDeclaredFields();
                    for (Field field : fields) {
                        field.setAccessible(true);
                        String fieldName = field.getName();
                        if ("created".equals(fieldName)||  "updated".equals(fieldName)){
                            document.addField(fieldName,formatDate((Date) field.get(tbItemNew)));
                        }else{
                            document.addField(fieldName,field.get(tbItemNew)); //相当于给每一条json添加键值
                        }

                    }
                    solrServer.add(document);
                    solrServer.commit();
                } catch (SolrServerException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                break;

        }
    }
    private String formatDate(Date date)  {
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        return  simpleDateFormat.format(date);
    }
}
