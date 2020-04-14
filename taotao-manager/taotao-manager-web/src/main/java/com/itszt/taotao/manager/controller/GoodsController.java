package com.itszt.taotao.manager.controller;

import com.itszt.taotao.easyui.bean.EasyUIPageDatasBean;
import com.itszt.taotao.easyui.bean.EasyUITreeBean;
import com.itszt.taotao.easyui.bean.ItemPicUtil;
import com.itszt.taotao.manager.service.inter.GoodsService;
import com.itszt.taotao.manager.vo.EasyUIPicUploadBean;
import com.itszt.taotao.manager.vo.EasyUISaveItemBean;
import com.itszt.taotao.pojo.TbItem;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;


@Controller
public class GoodsController {

    @Resource
    private GoodsService goodsService;
    @Resource
    private FastDFSUtil fastDFSUtil;
    @Value("${nginx_server}")
    private String nginx_server;

    private Logger logger = Logger.getLogger(GoodsController.class);

    //动态跳转
    @RequestMapping("{path}")
    public String goToItemList(@PathVariable(name = "path") String path){
        return path;
    }


    //这个是easyUI 内部的ajax 请求的 并返回数据      接收参数 : 当前页 每页显示数量
    @RequestMapping("/item/list")
    @ResponseBody      //false : 值 这个参数不是必须得 可以没有   defaultValue =默认值 如果前端没提交这个参数有个默认
                                //@RequestParam  还有一个作用就是 解决前后端键名不一致
    public EasyUIPageDatasBean<TbItem> getAllGoods(@RequestParam(required =false ,defaultValue="1") String page,
                                    @RequestParam(required = false,defaultValue = "20") String rows){
        //使用
        EasyUIPageDatasBean<TbItem> pageGoods = goodsService.getPageGoods(NumberUtils.toInt(page), NumberUtils.toInt(rows));
        return pageGoods;
    }

    @RequestMapping("/pic/upload")
    @ResponseBody     //{"error":0/1,"url":"xxxxxx","message":"xxxxxx"}
    public EasyUIPicUploadBean uploadPic(MultipartFile uploadFile){
        String[] split = uploadFile.getOriginalFilename().split("\\.");
        String extName=split[1];
        EasyUIPicUploadBean easyUIPicUploadBean = new EasyUIPicUploadBean();
        try {
            String s = fastDFSUtil.uploadFile(uploadFile.getBytes(), extName, null);
            String path =nginx_server+s;
            //String s1 = ItemPicUtil.genFullPath(s);
            logger.debug(path);
            easyUIPicUploadBean.setError(0);
            easyUIPicUploadBean.setMessage("上传成功");
            easyUIPicUploadBean.setUrl(path);
            return easyUIPicUploadBean;
        } catch (Exception e) {
            e.printStackTrace();
        }
        easyUIPicUploadBean.setMessage("上传失败");
        easyUIPicUploadBean.setError(1);
        return easyUIPicUploadBean;
    }


//添加商品
    @RequestMapping("/item/save")
    @ResponseBody
    public EasyUISaveItemBean saveItem(TbItem tbItem,String desc){
        tbItem.setImage(ItemPicUtil.genFdfsPath(tbItem.getImage()));
        EasyUISaveItemBean saveItemBean = new EasyUISaveItemBean();
        try {
            goodsService.addGoods(tbItem, desc);
            saveItemBean.setStatus(EasyUISaveItemBean.STATUS_OK);
            saveItemBean.setMessage("添加成功");
        } catch (Exception e) {
            e.printStackTrace();
            saveItemBean.setStatus(EasyUISaveItemBean.STATUS_ERROR);
            saveItemBean.setMessage("添加失败");
        }
        return saveItemBean;
    }




}
