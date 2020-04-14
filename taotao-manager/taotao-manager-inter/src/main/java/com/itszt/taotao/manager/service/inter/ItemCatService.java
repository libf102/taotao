package com.itszt.taotao.manager.service.inter;

import com.itszt.taotao.easyui.bean.EasyUITreeBean;

import java.util.List;

public interface ItemCatService {
    public List<EasyUITreeBean> getTopNodes();
    public List<EasyUITreeBean> getLeafNodes(String parent_id);
}
