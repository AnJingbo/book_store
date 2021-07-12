package com.atjingbo.dao;

import com.atjingbo.pojo.User;

public interface UserDao {
    /**
     * 根据用户名查询用户信息
     * @param username 用户民
     * @return 如果返回 null，说明没有这个用户
     */
    public User queryUserByUsername(String username);

    /**
     * 通过用户名和密码查询用户信息
     * @param username 用户名
     * @param password 密码
     * @return 如果返回 null，说明用户名或者密码错误
     */
    public User queryUserByUsernameAndPassword(String username, String password);

    /**
     * 保存用户信息
     * @param user 用户信息
     * @return 返回 -1 表示操作失败，其他是 sql 语句影响的行数
     */
    public int saveUser(User user);
}
