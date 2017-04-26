package com.endos.mmall.service;

import com.endos.mmall.common.ServiceResponse;
import com.endos.mmall.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by Endos on 2017/04/26.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/*.xml")
public class IUserServiceTest {
    @Autowired
    IUserService userService;

    @Test
    public void login() throws Exception {
        String username = "admin1";
        String password = "1234576";
        ServiceResponse<User> response = userService.login(username, password);
        System.out.println("-------------TestResult------------");
        System.out.println("STATUS:" + response.getStatus());
        System.out.println("DATA:" + response.getData());
        System.out.println("MSG:" + response.getMsg());
    }

}