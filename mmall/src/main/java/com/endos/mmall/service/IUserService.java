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
     * @param str
     * @param type
     * @return
     */
    ServiceResponse<String> checkValid(String str,String type);
}
