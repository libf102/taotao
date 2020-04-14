package com.itszt.taotao.easyui.bean;

import java.io.Serializable;

public class EasyUITreeBean implements Serializable {

    private long id;      //选择的类目 的 id  前端会提交这个id (第一次提交为"" 展示全部父节点      第二次根据提交的 id展示)
    private String state=STATE_CLOSED;     //默认为 closed 可以继续展开   isparent为0 则不能继续展开  设为open
    private String text;   //节点名字           谁弄得 closed 可以开  open 不能开  有点变态

    //父节点
    public static final String STATE_CLOSED="closed";

    //最终子节点
    public static final String STATE_OPEN="open";


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
