package com.itszt.taotao.manager.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.itszt.taotao.easyui.bean.EasyUITreeBean;
import com.itszt.taotao.manager.dao.ContentDao;
import com.itszt.taotao.manager.service.inter.ContentService;
import com.itszt.taotao.pojo.TbContent;
import com.itszt.taotao.pojo.TbContentCategory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class ContentServiceImpl implements ContentService {
    @Resource
    private ContentDao contentDao;
    @Value("${big_ad_key}")
    private String bigADKay;
    @Value("${big_ad_cid}")
    private String categoryId;
    @Resource
    private RedisTemplate redisTemplate;

    @Override
    public List<EasyUITreeBean> getTopNodes() {
        List<TbContentCategory> tbContentCategories = contentDao.queryTopNodes();
        List<EasyUITreeBean> convertbean = convertbean(tbContentCategories);

        return convertbean;
    }

    @Override// 参数是父节点的
    public List<EasyUITreeBean> getLeafNodes(String id) {
        List<TbContentCategory> tbContentCategories = contentDao.queryLeafNodes(id);
        List<EasyUITreeBean> convertbean = convertbean(tbContentCategories);
        return convertbean;
    }

    @Override
    @Transactional
    public boolean InsertContent(TbContent tbContent) {
        tbContent.setCreated(new Date());
        tbContent.setUpdated(new Date());
        contentDao.InsertContent(tbContent);
        redisTemplate.delete(bigADKay);
        return true;
    }

    @Override
    public List<TbContent> getContentByCategoryId() {
        List<TbContent> tbContents = null;
        Object o = redisTemplate.opsForValue().get(bigADKay);
        if (o==null) {
            tbContents= contentDao.queryContentByCategoryId(categoryId);
            redisTemplate.opsForValue().set(bigADKay, JSON.toJSONString(tbContents));
            redisTemplate.expire(bigADKay,1, TimeUnit.HOURS);
        }else{
            String tbContentsJson=String.valueOf(o);
            tbContents= JSON.parseArray(tbContentsJson,TbContent.class);
        }


        return tbContents;
    }

    List<EasyUITreeBean> convertbean(List<TbContentCategory> tbContentCategories){
        List<EasyUITreeBean> easyUITreeBeans = new ArrayList<>();
         for (TbContentCategory tb : tbContentCategories) {
             EasyUITreeBean easyUITreeBean = new EasyUITreeBean();
             easyUITreeBeans.add(easyUITreeBean);
             easyUITreeBean.setId(tb.getId());
             easyUITreeBean.setText(tb.getName());
             if (!tb.getIsParent()) {
                 easyUITreeBean.setState(EasyUITreeBean.STATE_OPEN);
             }
         }

        return easyUITreeBeans;
     }
}
