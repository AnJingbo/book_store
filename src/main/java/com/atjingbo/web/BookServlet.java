package com.atjingbo.web;

import com.atjingbo.pojo.Book;
import com.atjingbo.pojo.Page;
import com.atjingbo.service.BookService;
import com.atjingbo.service.impl.BookServiceImpl;
import com.atjingbo.utils.WebUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public class BookServlet extends BaseServlet {
    private BookService bookService = new BookServiceImpl();

    protected void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1、获取请求参数（封装成 Book 对象）
        //Book book = WebUtils.copyParamToBean(request.getParameterMap(), new Book());
        String name = request.getParameter("name");
        String author = request.getParameter("author");
        String price = request.getParameter("price");
        String sales = request.getParameter("sales");
        String stock = request.getParameter("stock");
        Book book = new Book(null, name, author, new BigDecimal(price), Integer.parseInt(sales), Integer.parseInt(stock), null);
        // 2、调用 bookService.addBook() 保存图书
        bookService.addBook(book);
        // 3、跳到图书管理页面 /manager/bookServlet?action=list 页面
        // request.getRequestDispatcher("/manager/bookServlet?action=list").forward(request, response);

        // 3、请求重定向到 /manager/bookServlet?action=list 页面
        // 请求转发的 / 表示到端口号

        int pageNo = WebUtils.parseInt(request.getParameter("pageNo"), 0);
        pageNo++;
        response.sendRedirect(request.getContextPath() + "/manager/bookServlet?action=page&pageNo=" + pageNo);
    }

    protected void delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1、获取请求的参数（图书编号：id）
        String id = request.getParameter("id");
        // 2、调用 bookService.deleteBookById(); 删除图书
        bookService.deleteBookById(new Integer(id));
        // 3、请求重定向到 /book/manager/bookServlet?action=list 页面
        response.sendRedirect(request.getContextPath() + "/manager/bookServlet?action=page&pageNo=" + request.getParameter("pageNo"));
    }

    protected void update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1、获取请求的参数--->封装成为 Book 对象
        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String author = request.getParameter("author");
        String price = request.getParameter("price");
        String sales = request.getParameter("sales");
        String stock = request.getParameter("stock");
        Book book = new Book(Integer.parseInt(id), name, author, new BigDecimal(price), Integer.parseInt(sales), Integer.parseInt(stock), null);
        // 2、调用 bookService.updateBook 修改图书
        bookService.updateBook(book);
        // 3、重定向回图书列表管理页面：/工程名/manager/bookServlet?action=list
        response.sendRedirect(request.getContextPath() + "/manager/bookServlet?action=page&pageNo=" + request.getParameter("pageNo"));
    }

    protected void getBook(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1、获取请求的图书编号
        String id = request.getParameter("id");
        // 2、调用 bookService.queryBookById 找到对应的 Book
        Book book = bookService.queryBookById(Integer.parseInt(id));
        // 3、保存图书到 request 域中
        request.setAttribute("book", book);
        // 4、请求转发发到：/pages/manager/book_edit.jsp 页面
        request.getRequestDispatcher("/pages/manager/book_edit.jsp").forward(request, response);
    }

    protected void list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1、通过 BookService 查询全部图书
        List<Book> books = bookService.queryBooks();
        // 2、把全部图书保存到 Request 域中
        request.setAttribute("books", books);
        // 3、请求转发到 /pages/manager/book_manager.jsp 页面
        request.getRequestDispatcher("/pages/manager/book_manager.jsp").forward(request, response);
    }

    protected void page(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1、获取请求的参数 pageNo 和 pageSize
        int pageNo = WebUtils.parseInt(request.getParameter("pageNo"), 1); // 默认值是 1，也就是默认是在第一页
        int pageSize = WebUtils.parseInt(request.getParameter("pageSize"), Page.PAGE_SIZE);
        // 2、调用 bookService.page(pageNo, pageSize) 获取 Page 对象
        Page page = bookService.page(pageNo, pageSize);
        page.setUrl("manager/bookServlet?action=page");
        // 3、保存 Page 对象到 request 域中
        request.setAttribute("page", page);
        // 4、请求转发到 /pages/manager/book_manager.jsp 页面
        request.getRequestDispatcher("/pages/manager/book_manager.jsp").forward(request, response);
    }
}
