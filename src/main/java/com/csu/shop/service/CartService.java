package com.csu.shop.service;

import com.csu.shop.domain.Cart;
import com.csu.shop.domain.CartItem;
import com.csu.shop.persistence.CartItemMapper;
import com.csu.shop.persistence.ItemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {
    @Autowired
    private CartItemMapper cartItemMapper;
    @Autowired
    private ItemMapper itemMapper;

    public Cart getCart(String username) {
        Cart cart = new Cart();
        List<CartItem> cartList = cartItemMapper.getCartItemsByUsername(username);
        System.out.println("service"+cartList.size()+"   "+cartList.toString());
        for (CartItem cartItem:cartList) {
            cartItem.setItem(itemMapper.getItem(cartItem.getItemId()));
            cartItem.setInStock(true);
            cart.addItem(cartItem.getItem(), true);
        }
        return cart;
    }

    public void deleteCart(String username) {
        cartItemMapper.deleteCart(username);
    }
}
