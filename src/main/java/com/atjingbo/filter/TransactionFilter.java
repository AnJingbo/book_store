package com.atjingbo.filter;

import com.atjingbo.utils.JdbcUtils;

import javax.servlet.*;
import java.io.IOException;
import java.rmi.RemoteException;

public class TransactionFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try {
            filterChain.doFilter(servletRequest, servletResponse);
            JdbcUtils.commitAndClose(); // 提交并关闭事务
        }catch (Exception e){
            JdbcUtils.rollbackAndClose(); // 回滚并关闭事务
            e.printStackTrace();
            throw new RuntimeException(e); // 把异常抛给 Tomcat 服务器，管理展示友好的错误页面
        }

    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }
}
