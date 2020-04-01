package com.csu.shop.persistence;

import com.csu.shop.domain.LineItem;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface LineItemMapper {
    List<LineItem> getLineItemsByOrderId(int orderId);

    void insertLineItem(LineItem lineItem);
}
