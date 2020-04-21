package com.itszt.taotao.shopcar.dao;

import com.itszt.taotao.pojo.TbShopcar;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopcarDao {

    @Select("select * from tb_shopcar where itemId=#{itemId} and userId=#{userid}")
    public TbShopcar queryShopCarItem(@Param("itemId") Long itemId,@Param("userid") Long userid);

    @Update("update  tb_shopcar  set tag=#{newTag},num=#{newNum},totalPrice=#{totalPrice} where id=#{shopCarid}")
    public void updateShopCarItem(Long shopCarid, int newNum, Long newTag, Integer totalPrice);

}
