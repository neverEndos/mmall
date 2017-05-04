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

    /**
     * 重置密码
     * @param username
     * @param newPassword
     * @param forgetToken
     * @return
     */
    ServiceResponse<String> forgetResetPassword(String username,String newPassword,String forgetToken);

    /**
     * 修改密码
     * @param user
     * @param oldPassword
     * @param newPassword
     * @return
     */
    ServiceResponse<String> resetPassword(User user, String oldPassword, String newPassword);

    /**
     * 更新用户信息
     * @param user
     * @return
     */
    ServiceResponse<User> updateInformation(User user);

    /**
     * 获取登陆用户信息
     * @param id
     * @return
     */
    ServiceResponse<User> getUserInfo(Integer id);

    /**
     * 检验是否为管理员
     * @param user
     * @return
     */
    ServiceResponse checkAdminRole(User user);
}
