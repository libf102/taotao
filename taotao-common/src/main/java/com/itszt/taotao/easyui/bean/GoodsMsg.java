package com.itszt.taotao.easyui.bean;

import com.itszt.taotao.pojo.TbItem;

import java.io.Serializable;

public class GoodsMsg implements Serializable {
    private String opt;   //行为  : 新增 删除 修改
    private TbItem tbItemOld;
    private TbItem tbItemNew;

    public String getOpt() {
        return opt;
    }

    public void setOpt(String opt) {
        this.opt = opt;
    }

    public TbItem getTbItemOld() {
        return tbItemOld;
    }

    public void setTbItemOld(TbItem tbItemOld) {
        this.tbItemOld = tbItemOld;
    }

    public TbItem getTbItemNew() {
        return tbItemNew;
    }

    public void setTbItemNew(TbItem tbItemNew) {
        this.tbItemNew = tbItemNew;
    }
}
