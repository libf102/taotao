package com.itszt.taotao.portal.controller;

import com.itszt.taotao.easyui.bean.ItemPicUtil;
import com.itszt.taotao.pojo.TbContent;

import java.util.ArrayList;
import java.util.List;

public class BigADOV {

    /**
     * srcB : http://image.taotao.com/images/2015/03/03/2015030304360302109345.jpg
     * height : 240
     * alt :
     * width : 670
     * src : http://image.taotao.com/images/2015/03/03/2015030304360302109345.jpg
     * widthB : 550
     * href : http://sale.jd.com/act/e0FMkuDhJz35CNt.html?cpdad=1DLSUE
     * heightB : 240
     */

    private String srcB;
    private int height;
    private String alt;
    private int width;
    private String src;
    private int widthB;
    private String href;
    private int heightB;

    public String getSrcB() {
        return srcB;
    }

    public void setSrcB(String srcB) {
        this.srcB = srcB;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public int getWidthB() {
        return widthB;
    }

    public void setWidthB(int widthB) {
        this.widthB = widthB;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public int getHeightB() {
        return heightB;
    }

    public void setHeightB(int heightB) {
        this.heightB = heightB;
    }

    public static List<BigADOV> convertBigADOV(List<TbContent> tbContents,int height,int width){
        List<BigADOV> bigADOVS = new ArrayList<>();
        for (TbContent tbContent : tbContents) {
            BigADOV bigADOV = new BigADOV();
            bigADOVS.add(bigADOV);
            bigADOV.setAlt(tbContent.getTitle());
            bigADOV.setHeight(height);
            bigADOV.setHeightB(height);
            bigADOV.setWidth(width);
            bigADOV.setWidthB(width);
            bigADOV.setSrc(ItemPicUtil.genFullPath(tbContent.getPic()));
            bigADOV.setSrcB(ItemPicUtil.genFullPath(tbContent.getPic2()));

        }

        return bigADOVS;
    }
}
