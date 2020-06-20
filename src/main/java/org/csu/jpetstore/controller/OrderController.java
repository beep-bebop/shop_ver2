package org.csu.jpetstore.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.csu.jpetstore.domain.Order;
import org.csu.jpetstore.domain.UserLog;
import org.csu.jpetstore.service.*;
import org.csu.jpetstore.utils.ReturnEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
public class OrderController {
    @Autowired
    OrderService orderService;
    @Autowired
    CartService cartService;
    @Autowired
    AccountService accountService;
    @Autowired
    LogService logService;
    @Autowired
    HttpServletRequest request;

    @PostMapping("/user/order")
    public ReturnEntity postOrder(@RequestBody Order order, @RequestAttribute String username) {
        JSONObject data = new JSONObject();
        order.initOrder(accountService.getAccount(username), cartService.getCartByUsername(username));
        order.setExpiryDate(order.getExpiryDate().substring(0, 7));
        if (order.getShipToFirstName() == null) {
            order.setShipToFirstName(order.getBillToFirstName());
            order.setShipToLastName(order.getBillToLastName());
            order.setShipAddress1(order.getBillAddress1());
            order.setShipAddress2(order.getBillAddress2());
            order.setShipCity(order.getBillCity());
            order.setShipState(order.getBillCountry());
            order.setShipZip(order.getBillZip());
            order.setShipCountry(order.getBillCountry());
        }
        orderService.insertOrder(order);
        cartService.deleteByUsername(username);
        data.put("order", order);
        UserLog log = new UserLog(new Date(), "用户:" + username + "提交订单,总价值:" + order.getTotalPrice());
//        logService.insertUserLog(log);
        return ReturnEntity.successResult(data);
    }

    @GetMapping("/listOrders")
    public ReturnEntity listOrders(){
        JSONObject data = new JSONObject();
        BigDecimal total = BigDecimal.valueOf(0);
        List<Order> orderList = orderService.getOrders();
        for(int i=0;i<orderList.size();i++) {
            Order order = orderList.get(i);
            total = total.add(order.getTotalPrice());
            System.out.println(total);
        }
        System.out.println(orderList);
        data.put("orders",orderList);
        data.put("total",total);
        return ReturnEntity.successResult(data);
    }



    @GetMapping("/user/orders")
    public ReturnEntity getOrders(@RequestParam Map<String,String> params) {
        JSONObject data = new JSONObject();
        System.out.println(params);
        List<Order> orderList = new ArrayList<>();
        if (params.get("status") == null || params.get("status").length() == 0 ) {
            if (params.get("username") == null || params.get("username").length() == 0)
                orderList = orderService.getOrders();
            else
                orderList = orderService.getOrdersByUsername(params.get("username"));
        } else {
            if (params.get("username") == null || params.get("username").length() == 0)
                orderList = orderService.getOrdersByStatus(params.get("status"));
            else
                orderList = orderService.getOrdersByUsernameAndStatus(params.get("username"),params.get("status"));
        }
        data.put("orders",orderList);
        return ReturnEntity.successResult(data);
    }

    @GetMapping("/user/order")
    public ReturnEntity getOrder(@RequestParam Map<String,String> params) {
        JSONObject data = new JSONObject();
        Order order = orderService.getOrder(Integer.parseInt(params.get("id")));
        data.put("order", order);
        return ReturnEntity.successResult(data);
    }


    @PostMapping("order/edit")
    public ReturnEntity updateOrder(@RequestBody String order){
        Order order1 = JSON.parseObject(order,Order.class);
        if (order1 == null) {
            return ReturnEntity.failedResult("缺少参数order");
        }
        if(order1.getUsername().isEmpty())
            return ReturnEntity.failedResult("不能为空");
        JSONObject data1 = new JSONObject();
        orderService.updateOrderAndInventory(order1);
        data1.put("order", order1);
        return ReturnEntity.successResult(data1);
    }

    @PostMapping("order/delete")
    public ReturnEntity deleteOrder(@RequestBody String params) {
        System.out.println(params);
        JSONObject data = JSON.parseObject(params);
        orderService.deleteOrder(Integer.parseInt(data.getString("orderId")));
        return ReturnEntity.successResult("deleted");
    }
}