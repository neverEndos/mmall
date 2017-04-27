package com.endos.mmall.service;

import com.endos.mmall.common.Const;
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

    private String username = "admin";
    private String question = "问题";
    private String answer = "答案";

    @Test
    public void login() throws Exception {
        String username = "admin1";
        String password = "1234576";
        ServiceResponse<User> response = userService.login(username, password);
        printResult(response);
    }

    @Test
    public void checkValid() throws  Exception {
        String username = "admin";
        String type = Const.USERNAME;
        ServiceResponse<String> response = userService.checkValid(username, type);
        printResult(response);
    }

    @Test
    public void selectQuestion() throws  Exception {
        String username = "admin";
        ServiceResponse<String> response = userService.selectQuestion(username);
        printResult(response);
    }

    @Test
    public void checkAnswer() throws Exception {
        ServiceResponse<String> response = userService.checkAnswer(username, question, answer);
        printResult(response);
    }

    private static void printResult(ServiceResponse response){
        System.out.println("-------------TestResult------------");
        System.out.println("STATUS:" + response.getStatus());
        System.out.println("DATA:" + response.getData());
        System.out.println("MSG:" + response.getMsg());
        System.out.println("ISSUCCESS:" + response.isSuccess());
    }
}