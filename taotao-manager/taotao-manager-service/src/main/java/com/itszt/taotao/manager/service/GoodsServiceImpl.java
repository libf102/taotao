package com.itszt.taotao.manager.service;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itszt.taotao.easyui.bean.EasyUIPageDatasBean;
import com.itszt.taotao.manager.dao.GoodsDao;
import com.itszt.taotao.manager.service.inter.GoodsService;
import com.itszt.taotao.pojo.TbItem;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
@Service
public class GoodsServiceImpl implements GoodsService {
    @Resource
    private GoodsDao goodsDao;


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
}
