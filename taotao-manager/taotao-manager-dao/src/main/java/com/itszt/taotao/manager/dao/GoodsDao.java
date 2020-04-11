package com.itszt.taotao.manager.dao;


import com.itszt.taotao.pojo.TbItem;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GoodsDao {
    @Select("select * from tb_item")
    public List<TbItem> queryAllItems();
}
