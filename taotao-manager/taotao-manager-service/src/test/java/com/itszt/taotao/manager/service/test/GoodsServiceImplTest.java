package com.itszt.taotao.manager.service.test;

import com.itszt.taotao.manager.service.inter.GoodsService;
import com.itszt.taotao.pojo.TbItem;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-config.xml")
public class GoodsServiceImplTest {
    @Resource
    private GoodsService goodsService;
    @Test
    public void testGetAllGoods(){
        List<TbItem> allGoods = goodsService.getAllGoods();

        Assert.assertNotNull(allGoods);
        Assert.assertEquals(3096 , allGoods.size());
    }

}
