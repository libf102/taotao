package com.itszt.taotao.manager.service.inter;

import com.itszt.taotao.easyui.bean.EasyUIPageDatasBean;
import com.itszt.taotao.pojo.TbItem;
import org.omg.CORBA.PUBLIC_MEMBER;

import java.util.List;

//商品业务
public interface GoodsService {

    //分页显示商品数据   返回 easyUI bean
    public EasyUIPageDatasBean<TbItem> getPageGoods(Integer pageNow,Integer pageCapacity);  //page 为当前页  capacity 为 页容量

    //添加商品数据
    public boolean addGoods(TbItem tbItem,String desc);
}
