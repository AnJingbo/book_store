package com.atjingbo.test;

import com.atjingbo.dao.OrderItemDao;
import com.atjingbo.dao.impl.OrderItemDaoImpl;
import com.atjingbo.pojo.OrderItem;
import org.junit.Test;

import java.math.BigDecimal;

public class OrderItemDaoTest {

    @Test
    public void saveOrderItem() {
        OrderItemDao orderItemDao = new OrderItemDaoImpl();
        orderItemDao.saveOrderItem(new OrderItem(null, "java升天教学", 1, new BigDecimal(99), new BigDecimal(99), "12345"));
        orderItemDao.saveOrderItem(new OrderItem(null, "java遁地教学", 2, new BigDecimal(80), new BigDecimal(160), "12345"));
        orderItemDao.saveOrderItem(new OrderItem(null, "meile", 1, new BigDecimal(1.1), new BigDecimal(1.1), "123456"));
    }
}