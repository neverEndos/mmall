package com.endos.mmall.controller.backend;

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
 * Created by Endos on 2017/05/04.
 */
@Controller
@RequestMapping("/manager/user")
public class UserManagerController {

    @Autowired
    private IUserService userService;

    @PostMapping("/login.do")
    @ResponseBody
    public ServiceResponse<User> login(String username, String password, HttpSession session) {
        ServiceResponse<User> response = userService.login(username, password);
        if (response.isSuccess()) {
            User user = response.getData();
            if (user.getRole() == Const.Role.ROLE_ADMIN) {
                session.setAttribute(Const.CURRENT_USER, user);
                return response;
            }
            return ServiceResponse.createByErrorMessage(Const.Message.NOT_ADMIN);
        }
        return response;
    }
}
