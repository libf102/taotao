package com.itszt.taotao.manager.dao;

import com.itszt.taotao.easyui.bean.EasyUITreeBean;
import com.itszt.taotao.pojo.TbContent;
import com.itszt.taotao.pojo.TbContentCategory;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContentDao {

    @Select("select * from tb_content_category where parent_id=0")
    public List<TbContentCategory> queryTopNodes();

    @Select("select * from tb_content_category where parent_id=#{id}")
    public List<TbContentCategory> queryLeafNodes(String parent_id);

    @Insert("insert into tb_content values(NULL,#{categoryId},#{title},#{subTitle},#{titleDesc},#{url},#{pic},#{pic2},#{content},#{created},#{updated})")
    public void InsertContent(TbContent tbContent);

    @Select("select * from tb_content where category_id=#{CategoryId}")
    public List<TbContent> queryContentByCategoryId(String CategoryId);
    //aaa

}
