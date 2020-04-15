package com.itszt.taotao.manager.service.inter;

import com.itszt.taotao.easyui.bean.EasyUITreeBean;
import com.itszt.taotao.pojo.TbContent;

import java.util.List;

public interface ContentService {
    public List<EasyUITreeBean> getTopNodes();
    public List<EasyUITreeBean> getLeafNodes(String id);

    public boolean InsertContent(TbContent tbContent);

    public List<TbContent> getContentByCategoryId();
}
