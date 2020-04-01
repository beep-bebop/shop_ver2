package com.csu.shop.persistence;

import com.csu.shop.domain.LineItem;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface LineItemMapper {
    List<LineItem> getLineItemsByOrderId(int orderId);

    void insertLineItem(LineItem lineItem);
}
