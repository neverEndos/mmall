package com.endos.mmall.service;

import com.endos.mmall.common.ServiceResponse;
import com.endos.mmall.entity.User;

/**
 * Created by Endos on 2017/04/26.
 */
public interface IUserService {

    /**
     * 用户登录
     * @param username
     * @param password
     * @return
     */
    ServiceResponse<User> login(String username, String password);

    /**
     * 用户注册
     * @param user
     * @return
     */
    ServiceResponse<String> register(User user);

    /**
     * 验证用户名或者email是否存在
     * @param str
     * @param type username或者email
     * @return
     */
    ServiceResponse<String> checkValid(String str,String type);

    /**
     * 用户忘记密码
     * @param username
     * @return
     */
    ServiceResponse<String> selectQuestion(String username);

    /**
     * 检查用户问题答案
     * @param username
     * @param question
     * @param answer
     * @return
     */
    ServiceResponse<String> checkAnswer(String username, String question, String answer);
}
