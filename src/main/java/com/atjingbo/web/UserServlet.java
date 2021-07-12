package com.atjingbo.web;

import com.atjingbo.pojo.User;
import com.atjingbo.service.UserService;
import com.atjingbo.service.impl.UserServiceImpl;
import com.google.gson.Gson;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY;

public class UserServlet extends BaseServlet {
    UserService userService = new UserServiceImpl();
    protected void ajaxExistsUsername(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取请求的参数 username
        String username = request.getParameter("username");
        // 调用 userService.existsUsername() 方法
        boolean existsUsername = userService.existsUsername(username);
        // 把返回的结果封装成为 map 对象
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("existsUsername", existsUsername);

        Gson gson = new Gson();
        String json = gson.toJson(resultMap);
        response.getWriter().write(json);
    }

    /**
     * 处理注销的功能
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1、销毁 Session 中用户登录的信息（或者销毁 Session）
        request.getSession().invalidate();
        // 2、重定向到首页（或者登陆页面）
        response.sendRedirect(request.getContextPath());
    }
        /**
         * 处理登陆的功能
         * @param request
         * @param response
         * @throws ServletException
         * @throws IOException
         */
    protected void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1、获取请求的参数
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        // 2、检查用户名和密码是否正确
        User loginUser = userService.login(new User(null, username, password, null));
        // 3、判断是否登陆成功
        if (loginUser == null) {// 登陆失败
            // 把错误信息和回显的表单项信息，保存到 Request 域中
            request.setAttribute("msg", "用户名或者密码错误");
            request.setAttribute("username", username);
            request.getRequestDispatcher("/pages/user/login.jsp").forward(request, response);
        } else {// 登陆成功
            // 保存用户登录之后的信息到 Session 域中
            request.getSession().setAttribute("user", loginUser);
            request.getRequestDispatcher("/pages/user/login_success.jsp").forward(request, response);
        }
    }

    /**
     * 处理注册的功能
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void regist(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取 Session 中的验证码（也就是获取 随机生成图片中的验证码）
        String token = (String)request.getSession().getAttribute(KAPTCHA_SESSION_KEY);
        // 删除 Session 中的验证码
        request.getSession().invalidate();
        // 1、获取请求的参数
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String code = request.getParameter("code");
        //User user = WebUtils.copyParamToBean(request.getParameterMap(), new User());
        //System.out.println(user);
        // 2、检查验证码是否正确
        if (token != null && token.equalsIgnoreCase(code)) {// 验证码正确
            // 3、检查用户名是否可用
            if (userService.existsUsername(username)) {
                System.out.println("用户名[" + username + "]已存在");
                // 把回显信息，保存到 Request 域中
                request.setAttribute("msg", "用户名已存在！");
                request.setAttribute("username", username);
                request.setAttribute("email", email);
                // 跳回到注册页面
                request.getRequestDispatcher("/pages/user/regist.jsp").forward(request, response);
            } else {
                // 调用 Service 保存到数据库
                userService.registUser(new User(null, username, password, email));
                // 跳到注册成功页面
                request.getRequestDispatcher("/pages/user/regist_success.jsp").forward(request, response);
            }
        } else {// 验证码错误
            // 把回显信息，保存到 Request 域中
            request.setAttribute("msg", "验证码错误！");
            request.setAttribute("username", username);
            request.setAttribute("email", email);
            System.out.println("验证码[" + code + "]错误");
            // 跳回到注册页面
            request.getRequestDispatcher("/pages/user/regist.jsp").forward(request, response);
        }
    }
}
