package com.atjingbo.web;

import com.atjingbo.pojo.User;
import com.atjingbo.service.UserService;
import com.atjingbo.service.impl.UserServiceImpl;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class LoginServlet extends HttpServlet {
    private UserService userService = new UserServiceImpl();
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1、获取请求的参数
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        // 2、检查用户名和密码是否正确
        User loginUser = userService.login(new User(null, username, password, null));
        // 3、判断是否登陆成功
        if(loginUser == null){// 登陆失败
            // 把错误信息和回显的表单项信息，保存到 Request 域中
            request.setAttribute("msg", "用户名或者密码错误");
            request.setAttribute("username", username);
            System.out.println("登陆失败");
            request.getRequestDispatcher("/pages/user/login.jsp").forward(request, response);
        }else{// 登陆成功
            System.out.println("登陆成功");
            request.getRequestDispatcher("/pages/user/login_success.jsp").forward(request, response);
        }
    }
}
