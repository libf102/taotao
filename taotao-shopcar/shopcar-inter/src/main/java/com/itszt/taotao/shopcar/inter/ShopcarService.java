package com.itszt.taotao.shopcar.inter;

import com.itszt.taotao.pojo.TbShopcar;

import java.util.List;

public interface ShopcarService {
    public boolean itemAddShopcar(long itemId,long userId);

    public List<TbShopcar> getShopcar(long userId);
}
