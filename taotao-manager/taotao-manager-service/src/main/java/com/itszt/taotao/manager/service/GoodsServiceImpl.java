package com.itszt.taotao.manager.service;


import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itszt.taotao.easyui.bean.EasyUIPageDatasBean;
import com.itszt.taotao.easyui.bean.GoodsMsg;
import com.itszt.taotao.manager.dao.GoodsDao;
import com.itszt.taotao.manager.service.inter.GoodsService;
import com.itszt.taotao.pojo.TbItem;
import com.itszt.taotao.pojo.TbItemDesc;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
@Service
public class GoodsServiceImpl implements GoodsService {
    @Resource
    private GoodsDao goodsDao;
    @Resource
    private KafkaTemplate kafkaTemplate;


    //分页显示商品数据   返回 easyUI bean
    @Override                                       //参数为 当前页  每页容量
    public EasyUIPageDatasBean<TbItem> getPageGoods(Integer pageNow, Integer pageCapacity) {
        //开始分页
        PageHelper.startPage(pageNow,pageCapacity);
        //查出所有数据
        List<TbItem> tbItems = goodsDao.queryAllItems();
        //把tbItems  传入 pageInfo    会自动生成一个 什么都有的 tbItemPageInfo
        PageInfo<TbItem> tbItemPageInfo = new PageInfo<>(tbItems);
        //获取总数
        long total = tbItemPageInfo.getTotal();
        //获取当前页数据
        List<TbItem> list = tbItemPageInfo.getList();
        EasyUIPageDatasBean<TbItem> tbItemEasyUIPageDatasBean = new EasyUIPageDatasBean<>();
        tbItemEasyUIPageDatasBean.setTotal(total);
        tbItemEasyUIPageDatasBean.setRows(list);

        return tbItemEasyUIPageDatasBean;    //返回的对象中有 前端所需要的的  数据总条数 当前页的数据
    }

    @Override
    @Transactional
    public boolean addGoods(TbItem tbItem, String desc) {
        tbItem.setStatus(0);
        tbItem.setCreated(new Date());
        tbItem.setUpdated(new Date());
        boolean b = goodsDao.insertTbItem(tbItem);

        TbItemDesc tbItemDesc = new TbItemDesc(); //订单描述对象

        tbItemDesc.setItemId(tbItem.getId());
        tbItemDesc.setCreated(new Date());
        tbItemDesc.setUpdated(new Date());
        tbItemDesc.setItemDesc(desc);
        boolean b1 = goodsDao.insertTbItemDesc(tbItemDesc);

        GoodsMsg goodsMsg = new GoodsMsg();
        goodsMsg.setOpt("add");
        goodsMsg.setTbItemNew(tbItem);
        kafkaTemplate.send("TbItemTopic", JSON.toJSONString(goodsMsg));


        return true;
    }

    @Override
    public TbItem getTbItemById(long goodsId) {
        TbItem tbItem = goodsDao.queryTbItem(goodsId);

        return tbItem;
    }

    @Override
    public TbItemDesc getTbItemDescById(long goodsId) {
        TbItemDesc tbItemDesc = goodsDao.queryTbItemDesc(goodsId);
        return tbItemDesc;
    }


}
