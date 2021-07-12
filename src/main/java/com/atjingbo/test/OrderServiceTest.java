package com.atjingbo.test;

import com.atjingbo.pojo.Cart;
import com.atjingbo.pojo.CartItem;
import com.atjingbo.service.OrderService;
import com.atjingbo.service.impl.OrderServiceImpl;
import org.junit.Test;

import java.math.BigDecimal;

public class OrderServiceTest {

    @Test
    public void createOrder() {
        OrderService orderService = new OrderServiceImpl();
        Cart cart = new Cart();
        cart.addItem(new CartItem(1, "如来神掌", 1, new BigDecimal(1000), new BigDecimal(1000)));
        cart.addItem(new CartItem(1, "如来神掌", 1, new BigDecimal(1000), new BigDecimal(1000)));
        cart.addItem(new CartItem(2, "九阴真经", 1, new BigDecimal(100), new BigDecimal(100)));

        orderService.createOrder(cart, 1);
    }
}