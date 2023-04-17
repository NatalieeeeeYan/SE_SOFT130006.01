package com.team.ShopSystem.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.team.ShopSystem.sys.entity.CartGoods;
import com.team.ShopSystem.sys.entity.Goods;

import java.util.List;

/**
 * @Author: Zhong Siqi
 * @Description: 购物车mapper
 * @DateTime: 2023/4/7 21:14
 **/
public interface CartGoodsMapper extends BaseMapper<CartGoods> {

    //Integer getQuantity(Integer cartId, Integer goodsId);

    Integer queryById(Integer cartId, Integer goodsId);

    void updateQuantity(Integer cartId, Integer goodsId,Integer variate);

    List<Goods> getByCartId(Integer cartId);

    Integer queryByGoodsId(Integer goodsId);

    void deleteBatch(Integer cartId, Integer goodsId);

    void deleteAll(Integer cartId);

    void setRemoved(Integer cartId);
}
