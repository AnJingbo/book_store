package com.atjingbo.service.impl;

import com.atjingbo.dao.BookDao;
import com.atjingbo.dao.OrderDao;
import com.atjingbo.dao.OrderItemDao;
import com.atjingbo.dao.impl.BookDaoImpl;
import com.atjingbo.dao.impl.OrderDaoImpl;
import com.atjingbo.dao.impl.OrderItemDaoImpl;
import com.atjingbo.pojo.*;
import com.atjingbo.service.OrderService;

import java.util.Date;
import java.util.Map;

public class OrderServiceImpl implements OrderService {
    private OrderDao orderDao = new OrderDaoImpl();
    private OrderItemDao orderItemDao = new OrderItemDaoImpl();
    private BookDao bookDao = new BookDaoImpl();
    @Override
    public String createOrder(Cart cart, Integer userId) {
        // 订单号（要保证唯一）
        String orderId = System.currentTimeMillis() + "" + userId;
        // 创建一个订单对象
        Order order = new Order(orderId, new Date(), cart.getTotalPrice(), 0, userId);
        // 保存订单
        orderDao.saveOrder(order);

        // 遍历购物车中的每一个商品项转换为订单项保存到数据库中
        for(Map.Entry<Integer, CartItem> entry : cart.getItems().entrySet()){
            // 获取购物车中的每个商品项
            CartItem cartItem = entry.getValue();
            // 将商品项转换为订单项
            OrderItem orderItem = new OrderItem(null, cartItem.getName(), cartItem.getCount(), cartItem.getPrice(), cartItem.getTotalPrice(), orderId);
            // 保存订单项到数据库中
            orderItemDao.saveOrderItem(orderItem);

            // 更新库存和销量
            Book book = bookDao.queryBookById(cartItem.getId());
            book.setStock(book.getStock() - cartItem.getCount());
            book.setSales(book.getSales() + cartItem.getCount());
            bookDao.updateBook(book);
        }
        // 清空购物车
        cart.clear();
        return orderId;
    }
}
