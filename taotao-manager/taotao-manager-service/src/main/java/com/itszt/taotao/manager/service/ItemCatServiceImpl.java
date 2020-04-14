package com.itszt.taotao.manager.service;

import com.itszt.taotao.easyui.bean.EasyUITreeBean;
import com.itszt.taotao.manager.dao.ItemCatDao;
import com.itszt.taotao.manager.service.inter.ItemCatService;
import com.itszt.taotao.pojo.TbItemCat;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
@Service
public class ItemCatServiceImpl implements ItemCatService {
    @Resource
    private ItemCatDao itemCatDao;

    @Override
    public List<EasyUITreeBean> getTopNodes() {
        List<TbItemCat> tbItemCats = itemCatDao.queryTopNodes();
        List<EasyUITreeBean> easyUITreeBeans = convertList(tbItemCats);

        return easyUITreeBeans;
    }

    @Override
    public List<EasyUITreeBean> getLeafNodes(String parent_id) {
        List<TbItemCat> tbItemCats = itemCatDao.queryLeafNodes(parent_id);
        List<EasyUITreeBean> easyUITreeBeans = convertList(tbItemCats);
        return easyUITreeBeans;
    }
    private List<EasyUITreeBean> convertList(List<TbItemCat> tbItemCats){
        List<EasyUITreeBean> easyUITreeBeans = new ArrayList<>();
        for (TbItemCat tbItemCat : tbItemCats) {
            EasyUITreeBean treeBean = new EasyUITreeBean();
            easyUITreeBeans.add(treeBean);
            treeBean.setId(tbItemCat.getId());
            treeBean.setText(tbItemCat.getName());
            if (!tbItemCat.getIsParent()) {
                treeBean.setState(EasyUITreeBean.STATE_OPEN);
            }
        }
        System.out.println(easyUITreeBeans);
        return easyUITreeBeans;
    }
}
