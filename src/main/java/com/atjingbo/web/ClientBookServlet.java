package com.atjingbo.web;

import com.atjingbo.pojo.Page;
import com.atjingbo.service.BookService;
import com.atjingbo.service.impl.BookServiceImpl;
import com.atjingbo.utils.WebUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class ClientBookServlet extends BaseServlet {
    private BookService bookService = new BookServiceImpl();

    protected void page(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1、获取请求的参数 pageNo 和 pageSize
        int pageNo = WebUtils.parseInt(request.getParameter("pageNo"), 1); // 默认值是 1，也就是默认是在第一页
        int pageSize = WebUtils.parseInt(request.getParameter("pageSize"), Page.PAGE_SIZE);
        // 2、调用 bookService.page(pageNo, pageSize) 获取 Page 对象
        Page page = bookService.page(pageNo, pageSize);
        page.setUrl("client/bookServlet?action=page");
        // 3、保存 Page 对象到 request 域中
        request.setAttribute("page", page);
        // 4、请求转发到 /pages/client/index.jsp 页面
        request.getRequestDispatcher("/pages/client/index.jsp").forward(request, response);
    }

    protected void pageByPrice(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1、获取请求的参数 pageNo 和 pageSize 和 min 和 max
        int pageNo = WebUtils.parseInt(request.getParameter("pageNo"), 1); // 默认值是 1，也就是默认是在第一页
        int pageSize = WebUtils.parseInt(request.getParameter("pageSize"), Page.PAGE_SIZE);
        int min = WebUtils.parseInt(request.getParameter("min"), 0);
        int max = WebUtils.parseInt(request.getParameter("max"), Integer.MAX_VALUE);
        // 2、调用 bookService.pageByPrice(pageNo, pageSize) 获取 Page 对象
        StringBuilder sb = new StringBuilder("client/bookServlet?action=pageByPrice");
        if(request.getParameter("min") != null){
            sb.append("&min=").append(request.getParameter("min"));
        }
        if(request.getParameter("max") != null){
            sb.append("&max=").append(request.getParameter("max"));
        }

        Page page = bookService.pageByPrice(pageNo, pageSize, min, max);
        page.setUrl(sb.toString());
        // 3、保存 Page 对象到 request 域中
        request.setAttribute("page", page);
        // 4、请求转发到 /pages/client/index.jsp 页面
        request.getRequestDispatcher("/pages/client/index.jsp").forward(request, response);
    }
}
