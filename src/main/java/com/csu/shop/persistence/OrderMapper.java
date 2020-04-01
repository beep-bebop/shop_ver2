package com.csu.shop.persistence;

import com.csu.shop.domain.Order;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OrderMapper {
    List<Order> getOrdersByUsername(String username);

    Order getOrder(int orderId);

    void insertOrder(Order order);

    void insertOrderStatus(Order order);
}
