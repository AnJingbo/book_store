package com.atjingbo.test;

import com.atjingbo.dao.OrderDao;
import com.atjingbo.dao.impl.OrderDaoImpl;
import com.atjingbo.pojo.Order;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Date;

public class OrderDaoTest {

    @Test
    public void saveOrder() {
        OrderDao orderDao = new OrderDaoImpl();
        orderDao.saveOrder(new Order("123456", new Date(), new BigDecimal(100), 0, 2));
    }
}