package com.itszt.taotao.manager.controller;

import com.itszt.taotao.easyui.bean.EasyUITreeBean;
import com.itszt.taotao.easyui.bean.ItemPicUtil;
import com.itszt.taotao.manager.service.inter.ContentService;
import com.itszt.taotao.manager.vo.EasyUISaveItemBean;
import com.itszt.taotao.pojo.TbContent;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class ContentController {
    @Resource
    private ContentService contentService;
    @RequestMapping("/content/category/list")
    public List<EasyUITreeBean> contentTree(String id){
        List<EasyUITreeBean> topNodes=null;
        if (StringUtils.isEmpty(id)) {
            topNodes= contentService.getTopNodes();
        }else{
            topNodes= contentService.getLeafNodes(id);
        }
        return  topNodes;

    }
    @RequestMapping("/content/save")
    @ResponseBody
    public EasyUISaveItemBean saveContent(TbContent tbContent){
        EasyUISaveItemBean easyUISaveItemBean = new EasyUISaveItemBean();

        try {
            tbContent.setPic(ItemPicUtil.genFdfsPath(tbContent.getPic()));
            tbContent.setPic2(ItemPicUtil.genFdfsPath(tbContent.getPic2()));
            contentService.InsertContent(tbContent);
            easyUISaveItemBean.setStatus(EasyUISaveItemBean.STATUS_OK);
            easyUISaveItemBean.setMessage("添加成功哈哈");
        } catch (Exception e) {
            e.printStackTrace();
            easyUISaveItemBean.setStatus(EasyUISaveItemBean.STATUS_ERROR);
            easyUISaveItemBean.setMessage("添加失败哈哈");

        }
        return  easyUISaveItemBean;
    }


}
