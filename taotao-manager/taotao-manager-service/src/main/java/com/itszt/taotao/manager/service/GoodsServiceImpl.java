package com.itszt.taotao.manager.service;


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
    @Override
    public List<TbItem> getAllGoods() {

        List<TbItem> tbItems = goodsDao.queryAllItems();

        return tbItems;
    }
}
