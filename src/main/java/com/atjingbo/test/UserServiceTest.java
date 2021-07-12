package com.atjingbo.test;

import com.atjingbo.pojo.User;
import com.atjingbo.service.UserService;
import com.atjingbo.service.impl.UserServiceImpl;
import org.junit.Test;

public class UserServiceTest {
    UserService userService = new UserServiceImpl();
    @Test
    public void registUser() {
        userService.registUser(new User(null, "bbj", "174638", "admin@bbj.com"));
    }

    @Test
    public void login() {
        if(userService.login(new User(null, "bbj", "174656438", "admin@bbj.com")) == null){
            System.out.println("登陆失败");
        }else{
            System.out.println("登陆成功");
        }
    }

    @Test
    public void existsUsername() {
        if(userService.existsUsername("admin321")){
            System.out.println("已包含该用户名");
        }else{
            System.out.println("用户名可用");
        }
    }
}