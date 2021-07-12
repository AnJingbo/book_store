package com.atjingbo.utils;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class JdbcUtils {
    private static DruidDataSource druidDataSource;
    private static ThreadLocal<Connection> conns = new ThreadLocal<>();
    static{
        try {
            Properties properties = new Properties();
            // 读取属性配置文件的内容
            InputStream inputStream = JdbcUtils.class.getClassLoader().getResourceAsStream("jdbc.properties");
            // 从流中加载数据
            properties.load(inputStream);
            // 创建数据库连接池
            druidDataSource = (DruidDataSource) DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取数据库连接池里的连接
     * @return 如果返回null，说明获取连接失败
     */
    public static Connection getConnection(){
        Connection conn = conns.get();
        if(conn == null){
            try {
                conn = druidDataSource.getConnection(); // 从数据库连接池中获取连接
                conns.set(conn); // 保存到 ThreadLocal 对象中，供后面的 jdbc 操作使用
                conn.setAutoCommit(false); // 设置为手动管理事务
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return conn;
    }

    /**
     * 提交事务并关闭连接
     */
    public static void commitAndClose(){
        Connection conn = conns.get();
        if(conn != null){ // 如果不为空，说明之前使用过连接，操作过数据库
            try {
                conn.commit(); // 提交事务
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } finally {
                try {
                    conn.close(); // 关闭连接，释放资源
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
        // 一定要 remove()，否则就会出错（因为 Tomcat 服务器底层使用了线程池技术）
        conns.remove();
    }

    /**
     * 释放事务并关闭连接
     */
    public static void rollbackAndClose(){
        Connection conn = conns.get();
        if(conn != null){ // 如果不为空，说明之前使用过连接，操作过数据库
            try {
                conn.rollback(); // 回滚事务
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } finally {
                try {
                    conn.close(); // 关闭连接，释放资源
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
        // 一定要 remove()，否则就会出错（因为 Tomcat 服务器底层使用了线程池技术）
        conns.remove();
    }
}
