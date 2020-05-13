package com.itszt.taotao.shopcar.dao;

import com.itszt.taotao.pojo.TbShopcar;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface ShopcarDao {

    @Select("select * from tb_shopcar where userId=#{userId} and itemId=#{itemId}")
    public TbShopcar queryShopCarItem(@Param("itemId") Long itemId,@Param("userId") Long userId);
                                                //Integer 可以为空
    @Update("update  tb_shopcar  set tag=#{newTag},num=#{newNum},totalPrice=#{totalPrice} where id=#{shopCarid}")
    public void updateShopCarItem(@Param("shopCarid") Long shopCarid, @Param("newNum")int newNum, @Param("newTag")Long newTag, @Param("totalPrice")Integer totalPrice);

    @Insert("insert into tb_shopcar values(NULL,#{userId},#{itemId},#{itemImg},#{itemTitle},#{price},#{totalPrice},#{num},#{addDate},#{tag})")
    public void insertShopCarItem(TbShopcar tbShopcar);

    @Select("select * from tb_shopcar where userId=#{userId} order by tag desc")
    public List<TbShopcar> queryUserShopcar(Long userId);

}
