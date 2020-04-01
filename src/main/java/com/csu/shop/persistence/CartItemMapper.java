package com.csu.shop.persistence;

import com.csu.shop.domain.CartItem;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Map;

@Repository
public interface CartItemMapper {
    List<CartItem> getCartItemsByUsername(String username);

    @MapKey("userid")
    Map<String, CartItem> getCartMapByUsername(String username);

    void insertCartItem(@Param("c") CartItem cartItem, @Param("username") String username);

    void deleteCart(String username);
}
