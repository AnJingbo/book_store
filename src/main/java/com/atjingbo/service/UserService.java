package com.atjingbo.service;

import com.atjingbo.pojo.User;

public interface UserService {
    /**
     * 注册用户
     * @param user
     */
    public void registUser(User user);

    /**
     * 登陆
     * @param user
     * @return
     */
    public User login(User user);

    /**
     * 检查用户名是否可用
     * @param username
     * @return 返回 true 表示用户名已经存在，返回 false 表示用户名可用
     */
    public boolean existsUsername(String username);
}
