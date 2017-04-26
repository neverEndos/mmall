package com.endos.mmall.service.impl;

import com.endos.mmall.common.Const;
import com.endos.mmall.common.ServiceResponse;
import com.endos.mmall.dao.UserMapper;
import com.endos.mmall.entity.User;
import com.endos.mmall.service.IUserService;
import com.endos.mmall.util.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Endos on 2017/04/26.
 */
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public ServiceResponse<User> login(String username, String password) {
        int resultCount = userMapper.checkUsername(username);
        if (resultCount == 0) {
            return ServiceResponse.createByError("用户名不存在");
        }

        //MD5加密
        User user = userMapper.selectLogin(username, MD5Util.getMD5(password));
        if (user == null) {
            return ServiceResponse.createByError("密码错误");
        }

        user.setPassword(StringUtils.EMPTY);
        return ServiceResponse.createBySuccess("登录成功", user);
    }

    @Override
    public ServiceResponse<String> register(User user) {
        return null;//todo
    }

    @Override
    public ServiceResponse<String> checkValid(String str, String type) {
        if(StringUtils.isNotBlank(type)){
            if(Const.USERNAME.equals(type)){
                int resultCount = userMapper.checkUsername(str);
                if (resultCount == 0) {
                    return ServiceResponse.createByError("用户名不存在");
                }
            }
            if(Const.EMAIL.equals(type)){

            }
        }else {
            return ServiceResponse.createByError("参数不能为空！");
        }
        return null;
    }


}
