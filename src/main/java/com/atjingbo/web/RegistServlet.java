package com.atjingbo.web;

import com.atjingbo.pojo.User;
import com.atjingbo.service.UserService;
import com.atjingbo.service.impl.UserServiceImpl;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class RegistServlet extends HttpServlet {
    private UserService userService = new UserServiceImpl();
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1、获取请求的参数
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String code = request.getParameter("code");
        // 2、检查验证码是否正确
        if("abcde".equalsIgnoreCase(code)){// 验证码正确
            // 3、检查用户名是否可用
            if(userService.existsUsername(username)){
                System.out.println("用户名[" + username + "]已存在");
                // 把回显信息，保存到 Request 域中
                request.setAttribute("msg", "用户名已存在！");
                request.setAttribute("username", username);
                request.setAttribute("email", email);
                // 跳回到注册页面
                request.getRequestDispatcher("/pages/user/regist.jsp").forward(request, response);
            }else{
                // 调用 Service 保存到数据库
                userService.registUser(new User(null, username, password, email));
                // 跳到注册成功页面
                request.getRequestDispatcher("/pages/user/regist_success.jsp").forward(request, response);
            }
        }else{// 验证码错误
            // 把回显信息，保存到 Request 域中
            request.setAttribute("msg", "验证码错误！");
            request.setAttribute("username", username);
            request.setAttribute("email", email);
            System.out.println("验证码["+ code +"]错误");
            // 跳回到注册页面
            request.getRequestDispatcher("/pages/user/regist.jsp").forward(request, response);
        }
    }
}
