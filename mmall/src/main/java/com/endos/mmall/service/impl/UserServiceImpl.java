package com.endos.mmall.service.impl;

import com.endos.mmall.common.Const;
import com.endos.mmall.common.ServiceResponse;
import com.endos.mmall.common.TokenCache;
import com.endos.mmall.dao.UserMapper;
import com.endos.mmall.entity.User;
import com.endos.mmall.service.IUserService;
import com.endos.mmall.util.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

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
            return ServiceResponse.createByErrorMessage("用户名不存在");
        }

        //MD5加密
        User user = userMapper.selectLogin(username, MD5Util.getMD5(password));
        if (user == null) {
            return ServiceResponse.createByErrorMessage("密码错误");
        }

        user.setPassword(StringUtils.EMPTY);
        return ServiceResponse.createBySuccess("登录成功", user);
    }

    @Override
    public ServiceResponse<String> register(User user) {
        // check用户名是否已经存在
        ServiceResponse validResponse = this.checkValid(user.getUsername(), Const.USERNAME);
        if (!validResponse.isSuccess()) {
            // 用户名已经存在
            return validResponse;
        }
        // check邮箱是否已经被注册
        validResponse = this.checkValid(user.getEmail(), Const.EMAIL);
        if (!validResponse.isSuccess()) {
            // 邮箱已存在
            return validResponse;
        }
        // 设置为普通用户权限
        user.setRole(Const.Role.ROLE_COSTOMER);
        // MD5加密密码
        user.setPassword(MD5Util.getMD5(user.getPassword()));
        // 更新数据库
        int resultCount = userMapper.insert(user);
        // 更新数据库失败，返回注册失败
        if (resultCount == 0) {
            return ServiceResponse.createByErrorMessage("注册失败");
        }
        // 更新数据库成功，返回注册成功
        return ServiceResponse.createBySuccessMessage("注册成功");
    }

    /**
     *
     * @param str
     * @param type username或者email
     * @return isSuccess为用户名不存在
     */
    @Override
    public ServiceResponse<String> checkValid(String str, String type) {
        if(StringUtils.isNotBlank(type)){
            if(Const.USERNAME.equals(type)){
                int resultCount = userMapper.checkUsername(str);
                if (resultCount > 0) {
                    return ServiceResponse.createByErrorMessage("用户名已存在");
                }
            }
            if(Const.EMAIL.equals(type)){
                int resultCount = userMapper.checkEmail(str);
                if (resultCount > 0) {
                    return ServiceResponse.createByErrorMessage("邮箱已被注册");
                }
            }
        }else {
            return ServiceResponse.createByErrorMessage("参数不能为空！");
        }
        return ServiceResponse.createBySuccessMessage("校验成功");
    }

    @Override
    public ServiceResponse<String> selectQuestion(String username) {
        ServiceResponse<String> validResponse = this.checkValid(username, Const.USERNAME);
        if (validResponse.isSuccess()) {
            // 用户名不存在
            return ServiceResponse.createByErrorMessage("用户名不存在");
        }
        String question = userMapper.selectQuestionByUsername(username);
        if (StringUtils.isNotBlank(question)) {
            return ServiceResponse.createBySuccess(question);
        }
        return ServiceResponse.createByErrorMessage("找回密码的问题是空的");
    }

    /**
     * 检查用户问题答案
     * @param username
     * @param question
     * @param answer
     * @return
     */
    @Override
    public ServiceResponse<String> checkAnswer(String username, String question, String answer) {
        int resultCount = userMapper.checkAnswer(username, question, answer);
        if (resultCount > 0) {
            //说明问题及问题答案是这个用户的,并且是正确的
            String forgetToken = UUID.randomUUID().toString();
            TokenCache.setKey(TokenCache.TOKEN_PREFIX + username, forgetToken);
            return ServiceResponse.createBySuccess(forgetToken);
        }
        // 回答错误
        return ServiceResponse.createByErrorMessage("问题的答案错误");
    }

}
