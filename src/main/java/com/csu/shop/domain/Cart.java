package com.csu.shop.domain;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;

@Component("cart")
public class Cart {
    private Map<String, CartItem> itemMap = Collections.synchronizedMap(new HashMap<>());
    private List<CartItem> itemList = new ArrayList<>();

    public void setItemList(List<CartItem> itemList) {
        this.itemList = itemList;
    }

    public void setItemMap(Map<String, CartItem> map) {
        this.itemMap = map;
    }

    public List<CartItem> getItemList() {
        return itemList;
    }

    public Iterator<CartItem> getCartItems() {
        return itemList.iterator();
    }

    public int getNumberOfItems() {
        return itemList.size();
    }

    public Iterator<CartItem> getAllCartItems() {
        return itemList.iterator();
    }

    public boolean containsItemId(String itemId) {
        return itemMap.containsKey(itemId);
    }

    public void addItem(Item item, boolean isInStock) {
        CartItem cartItem = itemMap.get(item.getItemId());
        if (cartItem == null) {
            cartItem = new CartItem();
            cartItem.setItemId(item.getItemId());
            cartItem.setItem(item);
            cartItem.setQuantity(1);
            cartItem.setInStock(isInStock);
            itemMap.put(item.getItemId(), cartItem);
            itemList.add(cartItem);
        }
        cartItem.incrementQuantity();
    }

    public Item removeItemById(String itemId) {
        CartItem cartItem = itemMap.remove(itemId);
        if (cartItem == null) {
            return null;
        } else {
            itemList.remove(cartItem);
            return cartItem.getItem();
        }
    }

    public void incrementQuantityByItemId(String itemId) {
        CartItem cartItem = itemMap.get(itemId);
        cartItem.incrementQuantity();
    }

    public void setQuantityByItemId(String itemId, int quantity) {
        CartItem cartItem = itemMap.get(itemId);
        cartItem.setQuantity(quantity);
    }

    public BigDecimal getSubTotal() {
        return itemList.stream()
                .map(cartItem -> cartItem.getItem().getListPrice().multiply(new BigDecimal(cartItem.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
