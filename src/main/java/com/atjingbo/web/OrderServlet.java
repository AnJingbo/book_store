package com.atjingbo.web;

import com.atjingbo.pojo.Cart;
import com.atjingbo.pojo.User;
import com.atjingbo.service.OrderService;
import com.atjingbo.service.impl.OrderServiceImpl;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class OrderServlet extends BaseServlet {

    private OrderService orderService = new OrderServiceImpl();
    /**
     * 生成订单
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void createOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 先获取 Cart 购物车对象
        Cart cart = (Cart)request.getSession().getAttribute("cart");
        // 获取 userId
        User loginUser = (User) request.getSession().getAttribute("user");
        // 调用 orderService.createOrder 方法生成订单
        if(loginUser == null){
            // 如果还没有登陆
            request.getRequestDispatcher("/pages/user/login.jsp").forward(request, response);
            return;
        }
        Integer userId = loginUser.getId();
        String orderId = orderService.createOrder(cart, userId);

        request.getSession().setAttribute("orderId", orderId);
        // 重定向到 /pages/cart/checkout.jsp
        response.sendRedirect(request.getContextPath() + "/pages/cart/checkout.jsp");
    }
}
