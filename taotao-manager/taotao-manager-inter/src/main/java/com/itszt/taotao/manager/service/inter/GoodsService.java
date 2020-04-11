package com.itszt.taotao.manager.service.inter;

import com.itszt.taotao.easyui.bean.EasyUIPageDatasBean;
import com.itszt.taotao.pojo.TbItem;

import java.util.List;

//商品业务
public interface GoodsService {

    public List<TbItem> getAllGoods();
    public EasyUIPageDatasBean<TbItem> getPageGoods(Integer pageNow,Integer pageCapacity);  //page 为当前页  capacity 为 页容量
}
