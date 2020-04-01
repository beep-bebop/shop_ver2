package com.csu.shop.persistence;

import com.csu.shop.domain.CartItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CartItemMapper {
    List<CartItem> getCartItemsByUsername(String username);

    void insertCartItem(@Param("c") CartItem cartItem, @Param("username") String username);

    void deleteCart(String username);
}
