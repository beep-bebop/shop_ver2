package com.csu.shop.controller;

import com.csu.shop.domain.Account;
import com.csu.shop.domain.Cart;
import com.csu.shop.domain.CartItem;
import com.csu.shop.domain.Item;
import com.csu.shop.service.CartService;
import com.csu.shop.service.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Iterator;

@Controller
@RequestMapping("/cart")
@SessionAttributes({"account", "itemId"})
public class CartController {

    @Autowired
    private CatalogService catalogService;
    @Autowired
    private CartService cartService;
    @Autowired
    private Cart cart;

    @GetMapping("/viewCart")
    public String viewCart(@SessionAttribute("account") Account account, Model model){
        System.out.println("TEST");
        System.out.println(account.getUsername());
        cart = cartService.getCart(account.getUsername());
        model.addAttribute("cart",cart);
        return "cart/Cart";
    }

    @GetMapping("/addItemToCart")
    public String addItemToCart(String workingItemId, Model model, @SessionAttribute("account") Account account){
        if(cart.containsItemId(workingItemId)){
            cart.incrementQuantityByItemId(workingItemId);
        }
        else{
            boolean isInStock = catalogService.isItemInStock(workingItemId);
            Item item = catalogService.getItem(workingItemId);
            cart.addItem(item,isInStock);
        }

        cartService.insertCart(cart,account.getUsername());

        model.addAttribute("cart", cart);
        return "cart/Cart";
    }

    @GetMapping("/removeItemFromCart")
    public String removeItemFromCart(String workingItem, Model model, @SessionAttribute("account") Account account){
        Item item = cart.removeItemById(workingItem);
        cartService.insertCart(cart,account.getUsername());
        if(item == null){
            model.addAttribute("message","Attempted to remove null CartItem from Cart");
            return "common/Error";
        }
        else{
            return "cart/Cart";
        }
    }

    @PostMapping("/updateCartQuantities")
    public String updateCartQuantities(@SessionAttribute("account") Account account, @SessionAttribute("itemId") int id, Model model){
        Iterator<CartItem> cartItemIterator = cart.getAllCartItems();

        while(cartItemIterator.hasNext()){
            //产品数量增加
            CartItem cartItem = cartItemIterator.next();
            String itemId = cartItem.getItem().getItemId();

            try {
                cart.setQuantityByItemId(itemId, id);
                if(id < 1){
                    cartItemIterator.remove();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        cartService.insertCart(cart,account.getUsername());
        model.addAttribute("cart", cart);
        return "cart/Cart";
    }

    public Cart getCart() {
        return cart;
    }
}
