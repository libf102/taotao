package com.itszt.taotao.manager.service;

import com.itszt.taotao.easyui.bean.EasyUITreeBean;
import com.itszt.taotao.manager.dao.ContentDao;
import com.itszt.taotao.manager.service.inter.ContentService;
import com.itszt.taotao.pojo.TbContent;
import com.itszt.taotao.pojo.TbContentCategory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Service
public class ContentServiceImpl implements ContentService {
    @Resource
    private ContentDao contentDao;

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
        return true;
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
