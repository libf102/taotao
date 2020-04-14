package com.itszt.taotao.manager.dao;

import com.itszt.taotao.pojo.TbItem;
import com.itszt.taotao.pojo.TbItemCat;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import javax.lang.model.element.NestingKind;
import java.util.List;

@Repository
public interface ItemCatDao {
    @Select("select * from tb_item_cat where parent_id=0")
    public List<TbItemCat> queryTopNodes();

    @Select("select * from tb_item_cat where parent_id=#{parent_id}")
    public List<TbItemCat> queryLeafNodes(String parent_id);
}
