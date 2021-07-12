package com.atjingbo.service;

import com.atjingbo.pojo.Cart;

public interface OrderService {
    public String createOrder(Cart cart, Integer userId);
}
