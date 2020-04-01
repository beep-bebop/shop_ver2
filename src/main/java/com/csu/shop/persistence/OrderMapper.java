package com.csu.shop.persistence;

import com.csu.shop.domain.Order;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderMapper {
    List<Order> getOrdersByUsername(String username);

    Order getOrder(int orderId);

    void insertOrder(Order order);

    void insertOrderStatus(Order order);
}
