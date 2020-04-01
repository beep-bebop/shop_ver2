package com.csu.shop;

import com.csu.shop.domain.Cart;
import com.csu.shop.service.CartService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ShopApplicationTests {
    @Autowired
    private CartService cartService;

    @Test
    void contextLoads() {
        Cart cart = cartService.getCart("wfy");
        System.out.print(cart.getItemList().get(0).getTotal()+cart.getItemList().get(0).getItemId()+cart.getItemList().get(0).getQuantity());
    }
}
