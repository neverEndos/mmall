package com.endos.mmall.controller.portal;

import com.endos.mmall.common.Const;
import com.endos.mmall.common.ResponseCode;
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
    @PostMapping(value = "/login.do")
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
    @PostMapping(value = "/logout.do")
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
    @PostMapping(value = "/register.do")
    @ResponseBody
    public ServiceResponse<String> register(User user){
        return userService.register(user);
    }

    /**
     * 验证用户名或者email是否存在
     * @param str
     * @param type username或者email
     * @return
     */
    @PostMapping(value = "/check_valid.do")
    @ResponseBody
    public ServiceResponse<String> check_valid(String str, String type){
        return userService.checkValid(str, type);
    }

    /**
     * 从Session中获取用户信息
     * @param session
     * @return
     */
    @PostMapping(value = "/get_user_info.do")
    @ResponseBody
    public ServiceResponse<User> getUserInfo(HttpSession session) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServiceResponse.createByErrorMessage(Const.Message.USER_NOT_LOGIN);
        }
        return ServiceResponse.createBySuccess(user);
    }

    /**
     * 用户忘记密码
     * @param username
     * @return
     */
    @PostMapping(value = "/forget_get_question.do")
    @ResponseBody
    public ServiceResponse<String> forgetGetQuestion(String username){
        return userService.selectQuestion(username);
    }

    /**
     * 检查问题答案是否正确
     * @param username
     * @param question
     * @param answer
     * @return
     */
    @PostMapping(value = "/forget_check_answer.do")
    @ResponseBody
    public ServiceResponse<String> forgetCheckAnswer(String username, String question, String answer){
        return userService.checkAnswer(username, question, answer);
    }

    /**
     * 重置密码
     * @param username
     * @param newPassword
     * @param forgetToken
     * @return
     */
    @PostMapping(value = "/forget_reset_password.do")
    @ResponseBody
    public ServiceResponse<String> forgetResetPassword(String username, String newPassword, String forgetToken){
        return userService.forgetResetPassword(username, newPassword, forgetToken);
    }

    /**
     * 修改密码
     * @param session
     * @param oldPassword
     * @param newPassword
     * @return
     */
    @PostMapping(value = "/reset_password.do")
    @ResponseBody
    public ServiceResponse<String> resetPassword(HttpSession session, String oldPassword, String newPassword) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServiceResponse.createByErrorMessage(Const.Message.USER_NOT_LOGIN);
        }
        return userService.resetPassword(user, oldPassword, newPassword);
    }

    /**
     * 更新用户信息
     * @param session
     * @param user
     * @return
     */
    @PostMapping(value = "/update_user_info.do")
    @ResponseBody
    public ServiceResponse<User> updateUserInfo(HttpSession session, User user) {
        User currentUser = (User) session.getAttribute(Const.CURRENT_USER);
        if (currentUser == null) {
            return ServiceResponse.createByErrorMessage(Const.Message.USER_NOT_LOGIN);
        }
        // ID,用户名不能修改
        user.setId(currentUser.getId());
        user.setUsername(currentUser.getUsername());
        ServiceResponse<User> response = userService.updateInformation(user);
        if(response.isSuccess()){
            response.getData().setUsername(currentUser.getUsername());
            session.setAttribute(Const.CURRENT_USER, response.getData());
        }
        return response;
    }

    /**
     * 获取登陆用户信息
     * @param session
     * @return
     */
    @PostMapping(value = "/get_information.do")
    @ResponseBody
    public ServiceResponse<User> getInformation(HttpSession session) {
        User currentUser = (User) session.getAttribute(Const.CURRENT_USER);
        if (currentUser == null) {
            return ServiceResponse.createByError(ResponseCode.NEED_LOGIN.getCode(), Const.Message.NEED_LOGIN);
        }
        return userService.getUserInfo(currentUser.getId());
    }

}
