package com.atjingbo.web;

import com.atjingbo.pojo.Book;
import com.atjingbo.pojo.Cart;
import com.atjingbo.pojo.CartItem;
import com.atjingbo.service.BookService;
import com.atjingbo.service.impl.BookServiceImpl;
import com.atjingbo.utils.WebUtils;
import com.google.gson.Gson;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CartServlet extends BaseServlet {
    private BookService bookService = new BookServiceImpl();

    protected void ajaxAddItem(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取请求的参数：商品编号
        int id = WebUtils.parseInt(request.getParameter("id"), 0);
        // 调用 bookService.queryBookById() 得到图书信息
        Book book = bookService.queryBookById(id);
        // 把图书信息，转换成 CartItem 商品项
        CartItem cartItem = new CartItem(book.getId(), book.getName(), 1, book.getPrice(), book.getPrice());
        // 调用 cart.addItem() 添加商品项
        Cart cart = (Cart)request.getSession().getAttribute("cart");
        if(cart == null){
            cart = new Cart();
            request.getSession().setAttribute("cart", cart);
        }
        cart.addItem(cartItem);
        // 保存最后添加的图书的名称
        request.getSession().setAttribute("lastName", cartItem.getName());
        // 返回购物车总的商品数量和最后一个添加的商品名称
        Map<String, Object> resultMap = new HashMap<>();

        resultMap.put("totalCount", cart.getTotalCount());
        resultMap.put("lastName", cartItem.getName());
        
        Gson gson = new Gson();
        String json = gson.toJson(resultMap);
        response.getWriter().write(json);
    }

    // 添加商品项
    protected void addItem(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取请求的参数：商品编号
        int id = WebUtils.parseInt(request.getParameter("id"), 0);
        // 调用 bookService.queryBookById() 得到图书信息
        Book book = bookService.queryBookById(id);
        // 把图书信息，转换成 CartItem 商品项
        CartItem cartItem = new CartItem(book.getId(), book.getName(), 1, book.getPrice(), book.getPrice());
        // 调用 cart.addItem() 添加商品项
        Cart cart = (Cart)request.getSession().getAttribute("cart");
        if(cart == null){
            cart = new Cart();
            request.getSession().setAttribute("cart", cart);
        }
        cart.addItem(cartItem);
        // 保存最后添加的图书的名称
        request.getSession().setAttribute("lastName", cartItem.getName());
        // 重定向到 新添加图书所在页
        response.sendRedirect(request.getHeader("Referer"));
    }
    // 删除商品项
    protected void deleteItem(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取商品编号
        int id = WebUtils.parseInt(request.getParameter("id"), 0);

        // 获取购物车对象
        Cart cart = (Cart)request.getSession().getAttribute("cart");
        if(cart != null){
            // 删除了购物车商品项
            cart.deleteItem(id);
        }
        // 重定向到 原来购物车的展示页面
        response.sendRedirect(request.getHeader("Referer"));
    }

    // 清空购物车
    protected void clear(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取购物车对象
        Cart cart = (Cart)request.getSession().getAttribute("cart");
        if(cart != null){
            // 清空购物车
            cart.clear();
        }
        // 重定向到 原来购物车的展示页面
        response.sendRedirect(request.getHeader("Referer"));
    }

    // 修改商品数量
    protected void updateCount(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取请求的参数：商品编号和商品数量
        int id = WebUtils.parseInt(request.getParameter("id"), 0);
        int count = WebUtils.parseInt(request.getParameter("count"), 0);
        // 获取购物车对象
        Cart cart = (Cart)request.getSession().getAttribute("cart");
        if(cart != null){
            // 修改商品数量
            cart.update(id, count);
        }
        // 重定向到 原来购物车的展示页面
        response.sendRedirect(request.getHeader("Referer"));
    }
}
