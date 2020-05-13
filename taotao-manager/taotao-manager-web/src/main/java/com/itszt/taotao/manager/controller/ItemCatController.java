package com.itszt.taotao.manager.controller;

import com.itszt.taotao.easyui.bean.EasyUITreeBean;
import com.itszt.taotao.manager.service.inter.ItemCatService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
public class ItemCatController {
    @Resource
    private ItemCatService itemCatService;

    @RequestMapping("/item/cat/list")
    @ResponseBody
    public List<EasyUITreeBean> getTreeNodes(String id){
        List<EasyUITreeBean> nodes=null;
        if (StringUtils.isEmpty(id)) {
            nodes = itemCatService.getTopNodes();  //id为空 第一次提交  查询所有的 顶层 节点  (parent_id为o)
        }else {
            nodes= itemCatService.getLeafNodes(id);  //根据提交的id 查父节点为这个id 的 所有节点
        }

        return nodes;
    }
}
