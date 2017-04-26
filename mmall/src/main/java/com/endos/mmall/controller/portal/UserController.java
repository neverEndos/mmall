package com.endos.mmall.controller.portal;

import com.endos.mmall.common.Const;
import com.endos.mmall.common.ServiceResponse;
import com.endos.mmall.entity.User;
import com.endos.mmall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * Created by Endos on 2017/04/26.
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;

    /**
     * 用户登录
     * @param username
     * @param password
     * @param session
     * @return
     */
    @PostMapping(value = "/login")
    @ResponseBody
    public ServiceResponse<User> login(String username, String password, HttpSession session) {
        ServiceResponse<User> response = userService.login(username, password);
        if (response.isSuccess()) {
            // session.setMaxInactiveInterval(60 * 30);
            session.setAttribute(Const.CURRENT_USER, response);
        }
        return response;
    }

    /**
     * 用户注销
     * @param session
     * @return
     */
    @PostMapping(value = "/logout")
    @ResponseBody
    public ServiceResponse<String> logout(HttpSession session){
        session.removeAttribute(Const.CURRENT_USER);
        return ServiceResponse.createBySuccess();
    }

    /**
     * 用户注册
     * @param user
     * @return
     */
    @PostMapping(value = "/register")
    @ResponseBody
    public ServiceResponse<String> register(User user){
        return null;//todo
    }
}
