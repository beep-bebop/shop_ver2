package com.csu.shop.service;

import com.csu.shop.domain.Item;
import com.csu.shop.domain.Order;
import com.csu.shop.domain.Sequence;
import com.csu.shop.persistence.ItemMapper;
import com.csu.shop.persistence.LineItemMapper;
import com.csu.shop.persistence.OrderMapper;
import com.csu.shop.persistence.SequenceMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderService {
    private final ItemMapper itemMapper;
    private final OrderMapper orderMapper;
    private final SequenceMapper sequenceMapper;
    private final LineItemMapper lineItemMapper;

    public OrderService(ItemMapper itemMapper, OrderMapper orderMapper, SequenceMapper sequenceMapper,
                        LineItemMapper lineItemMapper) {
        this.itemMapper = itemMapper;
        this.orderMapper = orderMapper;
        this.sequenceMapper = sequenceMapper;
        this.lineItemMapper = lineItemMapper;
    }

    @Transactional
    public void insertOrder(Order order) {
        order.setOrderId(getNextId("ordernum"));
        order.getLineItems().forEach(lineItem -> {
            String itemId = lineItem.getItemId();
            Integer increment = lineItem.getQuantity();
            Map<String, Object> param = new HashMap<>(2);
            param.put("itemId", itemId);
            param.put("increment", increment);
            itemMapper.updateInventoryQuantity(param);
        });

        orderMapper.insertOrder(order);
        orderMapper.insertOrderStatus(order);
        order.getLineItems().forEach(lineItem -> {
            lineItem.setOrderId(order.getOrderId());
            lineItemMapper.insertLineItem(lineItem);
        });
    }

    @Transactional
    public Order getOrder(int orderId) {
        Order order = orderMapper.getOrder(orderId);
        order.setLineItems(lineItemMapper.getLineItemsByOrderId(orderId));

        order.getLineItems().forEach(lineItem -> {
            Item item = itemMapper.getItem(lineItem.getItemId());
            item.setQuantity(itemMapper.getInventoryQuantity(lineItem.getItemId()));
            lineItem.setItem(item);
        });

        return order;
    }

    public List<Order> getOrdersByUsername(String username) {
        return orderMapper.getOrdersByUsername(username);
    }

    public int getNextId(String name) {
        Sequence sequence = sequenceMapper.getSequence(new Sequence(name, -1));
        if (sequence == null) {
            throw new RuntimeException(
                    "Error: A null sequence was returned from the database (could not get next " + name + " sequence).");
        }
        Sequence parameterObject = new Sequence(name, sequence.getNextId() + 1);
        sequenceMapper.updateSequence(parameterObject);
        return sequence.getNextId();
    }
}