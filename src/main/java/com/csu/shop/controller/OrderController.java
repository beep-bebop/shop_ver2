package com.csu.shop.controller;

import com.csu.shop.domain.Account;
import com.csu.shop.domain.Cart;
import com.csu.shop.domain.Order;
import com.csu.shop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Controller
@RequestMapping("/order")
@SessionAttributes({"account","order","shippingAddressRequired","confirmed","orderList"})
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private Order order;

    private static final List<String> CARD_TYPE_LIST ;

    static {
        List<String> cardList = new ArrayList<String>();
        cardList.add("Visa");
        cardList.add("MasterCard");
        cardList.add("American Express");
        CARD_TYPE_LIST = Collections.unmodifiableList(cardList);
    }

    @GetMapping("/listOrders")
    public String listOrders(Model model, @SessionAttribute("account") Account account){

        List<Order> orderList = orderService.getOrdersByUsername(account.getUsername());


        model.addAttribute("orderList",orderList);
        model.addAttribute("order",order);

        return "/order/ListOrders";
    }

    @GetMapping("/newOrderForm")
    public String newOrderForm(@SessionAttribute("account") Account account,@SessionAttribute("cart") Cart cart,Model model){
        order=new Order();
        boolean shippingAddressRequired = false;
        boolean confirmed = false;
        List<Order> orderList =null;
        System.out.println("AAAAA"+account.getUsername());
        if(account==null){
            String msg="You must sign on before attempting to check out.  Please sign on and try checking out again.";
            System.out.println(msg);
            model.addAttribute("msg", msg);
            return "/catalog/Main";
        }else if(cart!=null){
            order.initOrder(account,cart);
            model.addAttribute("order",order);
            model.addAttribute("shippingAddressRequired",shippingAddressRequired);
            model.addAttribute("confirmed",confirmed);
            model.addAttribute("orderList",orderList);
            return "/order/NewOrderForm";
        }else{
            String msg="An order could not be created because a cart could not be found.";
            model.addAttribute("msg", msg);
            return "redirect:/common/Error";
        }
    }

    @GetMapping("/newOrder")
    public String newOrder(HttpServletRequest request,Model model){
        boolean shippingAddressRequired= (boolean) request.getSession().getAttribute("shippingAddressRequired");
        if(shippingAddressRequired){
            shippingAddressRequired=false;
            model.addAttribute("shippingAddressRequired",shippingAddressRequired);
            return "/order/ShippingForm";
        }else if(! (boolean)request.getSession().getAttribute("confirmed")){
            return "/order/ConfirmOrder";
        }else if(request.getSession().getAttribute("order")!=null){

            orderService.insertOrder(order);

            CartController cartController = (CartController) request.getSession().getAttribute("/controller/Cart.controller");
            Cart cart =new Cart();
            String workingItemId = null;
            model.addAttribute("cart",cart);
            model.addAttribute("workingItemId",workingItemId);
            String msg ="Thank you, your order has been submitted.";
            model.addAttribute("msg",msg);
            return "/order/ViewOrder";
        }else{
            String msg ="An error occurred processing your order (order was null).";
            return "redirect:/common/Error";
        }
    }

    @GetMapping("/viewOrder")
    public String viewOrder(HttpServletRequest request,Model model){
        HttpSession session = request.getSession();

        AccountController accountController = (AccountController) session.getAttribute("accountController");

        order = orderService.getOrder(order.getOrderId());

        if(accountController.getAccount().getUsername().equals(order.getUsername())){
            model.addAttribute("order",order);
            return "/order/ViewOrder";
        }else {
            order=null;
            String msg = "You may only view your own orders.";
            model.addAttribute("msg",msg);
            model.addAttribute("order",order);
            return "redirect:/common/Error";
        }
    }
}
