package com.csu.shop;

import com.csu.shop.domain.Product;
import com.csu.shop.service.CartService;
import com.csu.shop.service.CatalogService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ShopApplicationTests {
    @Autowired
    private CartService cartService;
    @Autowired
    private CatalogService catalogService;
    @Test
    void contextLoads() {
        Product product = catalogService.getProduct("AV-SB-02");
        if(product != null)
            System.out.println("BBBBBBBBBBB"+product.getProductId()+product.getCategoryId());
        else
            System.out.println("zhaobudaoaaaaaaaaaaaaa");
//        Cart cart = cartService.getCart("wfy");
//        System.out.print(cart.getItemList().get(0).getTotal()+cart.getItemList().get(0).getItemId()+cart.getItemList().get(0).getQuantity());
    }
}
