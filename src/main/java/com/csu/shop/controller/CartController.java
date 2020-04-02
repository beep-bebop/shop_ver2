package com.csu.shop.controller;

import com.csu.shop.domain.Cart;
import com.csu.shop.domain.CartItem;
import com.csu.shop.domain.Item;
import com.csu.shop.service.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Iterator;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CatalogService catalogService;

    @Autowired
    private Cart cart;

    @GetMapping("/viewCart")
    public String viewCart(Model model){
        model.addAttribute("cart",cart);
        return "cart/cart";
    }

    @GetMapping("/addItemToCart")
    public String addItemToCart(String workingItemId, Model model){
        if(cart.containsItemId(workingItemId)){
            cart.incrementQuantityByItemId(workingItemId);
        }
        else{
            boolean isInStock = catalogService.isItemInStock(workingItemId);
            Item item = catalogService.getItem(workingItemId);
            cart.addItem(item,isInStock);
        }
        model.addAttribute("cart", cart);
        return "cart/Cart";
    }

    @GetMapping("/removeItemFromCart")
    public String removeItemFromCart(String workingItem, Model model){
        Item item = cart.removeItemById(workingItem);
        if(item == null){
            model.addAttribute("message","Attempted to remove null CartItem from Cart");
            return "common/error";
        }
        else{
            return "cart/cart";
        }
    }

    @GetMapping("/updateCartQuantities")
    public String updateCartQuantities(HttpServletRequest request, Model model){
        Iterator<CartItem> cartItemIterator = cart.getAllCartItems();

        while(cartItemIterator.hasNext()){
            //产品数量增加
            CartItem cartItem = cartItemIterator.next();
            String itemId = cartItem.getItem().getItemId();

            try {
                int quantity = Integer.parseInt(request.getParameter(itemId));
                cart.setQuantityByItemId(itemId, quantity);
                if(quantity < 1){
                    cartItemIterator.remove();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        model.addAttribute("cart", cart);
        return "cart/cart";
    }
}
