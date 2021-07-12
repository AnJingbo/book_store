package com.atjingbo.service.impl;

import com.atjingbo.dao.UserDao;
import com.atjingbo.dao.impl.UserDaoImpl;
import com.atjingbo.pojo.User;
import com.atjingbo.service.UserService;

public class UserServiceImpl implements UserService {
    private UserDao userDao = new UserDaoImpl();
    @Override
    public void registUser(User user) {
        userDao.saveUser(user);
    }

    @Override
    public User login(User user) {
        return userDao.queryUserByUsernameAndPassword(user.getUsername(), user.getPassword());
    }

    @Override
    public boolean existsUsername(String username) {
        if(userDao.queryUserByUsername(username) == null){
            // 等于 null，说明没查到，表示可用
            return false;
        }
        return true;
    }
}
