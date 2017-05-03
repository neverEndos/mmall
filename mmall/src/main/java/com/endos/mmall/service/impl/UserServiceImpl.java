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
            return ServiceResponse.createByErrorMessage(Const.Message.USER_NOT_EXIST);
        }

        //MD5加密
        User user = userMapper.selectLogin(username, MD5Util.getMD5(password));
        if (user == null) {
            return ServiceResponse.createByErrorMessage(Const.Message.PASSWORD_WRONG);
        }

        user.setPassword(StringUtils.EMPTY);
        return ServiceResponse.createBySuccess(Const.Message.LOGIN_SUCCESS, user);
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
            return ServiceResponse.createByErrorMessage(Const.Message.REGISTER_FAIL);
        }
        // 更新数据库成功，返回注册成功
        return ServiceResponse.createBySuccessMessage(Const.Message.REGISTER_SUCCESS);
    }

    /**
     * @param str
     * @param type username或者email
     * @return isSuccess为用户名不存在
     */
    @Override
    public ServiceResponse<String> checkValid(String str, String type) {
        if (StringUtils.isNotBlank(type)) {
            if (Const.USERNAME.equals(type)) {
                int resultCount = userMapper.checkUsername(str);
                if (resultCount > 0) {
                    return ServiceResponse.createByErrorMessage(Const.Message.USER_EXIST);
                }
            }
            if (Const.EMAIL.equals(type)) {
                int resultCount = userMapper.checkEmail(str);
                if (resultCount > 0) {
                    return ServiceResponse.createByErrorMessage(Const.Message.EMAIL_EXIST);
                }
            }
        } else {
            return ServiceResponse.createByErrorMessage(Const.Message.EMPTY_PARAM);
        }
        return ServiceResponse.createBySuccessMessage(Const.Message.VALID_SUCCESS);
    }

    @Override
    public ServiceResponse<String> selectQuestion(String username) {
        ServiceResponse<String> validResponse = this.checkValid(username, Const.USERNAME);
        if (validResponse.isSuccess()) {
            // 用户名不存在
            return ServiceResponse.createByErrorMessage(Const.Message.USER_NOT_EXIST);
        }
        String question = userMapper.selectQuestionByUsername(username);
        if (StringUtils.isNotBlank(question)) {
            return ServiceResponse.createBySuccess(question);
        }
        return ServiceResponse.createByErrorMessage(Const.Message.EMPTY_QUESTION);
    }

    /**
     * 检查用户问题答案
     *
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
        return ServiceResponse.createByErrorMessage(Const.Message.ANSWER_WRONG);
    }

    /**
     * 重置密码
     *
     * @param username
     * @param newPassword
     * @param forgetToken
     * @return
     */
    @Override
    public ServiceResponse<String> forgetResetPassword(String username, String newPassword, String forgetToken) {
        if (StringUtils.isBlank(forgetToken)) {
            return ServiceResponse.createByErrorMessage(Const.Message.EMPTY_TOKEN);
        }
        ServiceResponse validResponse = this.checkValid(username, Const.USERNAME);
        if (validResponse.isSuccess()) {
            // 用户名不存在
            return ServiceResponse.createByErrorMessage(Const.Message.USER_NOT_EXIST);
        }
        String token = TokenCache.getKey(TokenCache.TOKEN_PREFIX + username);
        if (StringUtils.isBlank(token)) {
            return ServiceResponse.createByErrorMessage(Const.Message.TOKEN_INVALID);
        }
        if (StringUtils.equals(forgetToken, token)) {
            // token正确,重置密码
            String md5Password = MD5Util.getMD5(newPassword);
            int resultCount = userMapper.updatePasswordByUsername(username, md5Password);
            if (resultCount > 0) {
                return ServiceResponse.createBySuccessMessage(Const.Message.CHANGE_PASSWORD_SUCCESS);
            }
        } else {
            return ServiceResponse.createByErrorMessage(Const.Message.TOKEN_WRONG);
        }
        return ServiceResponse.createByErrorMessage(Const.Message.CHANGE_PASSWORD_FAIL);
    }

    /**
     * 修改密码
     *
     * @param user
     * @param oldPassword
     * @param newPassword
     * @return
     */
    @Override
    public ServiceResponse<String> resetPassword(User user, String oldPassword, String newPassword) {
        //防止横向越权,要校验一下这个用户的旧密码,一定要指定是这个用户.因为我们会查询一个count(1),如果不指定id,那么结果就是true啦count>0;
        int resultCount = userMapper.checkPassword(MD5Util.getMD5(oldPassword), user.getId());
        if (resultCount == 0) {
            return ServiceResponse.createByErrorMessage(Const.Message.PASSWORD_WRONG);
        }
        user.setPassword(MD5Util.getMD5(newPassword));
        resultCount = userMapper.updateByPrimaryKeySelective(user);
        if (resultCount > 0) {
            return ServiceResponse.createBySuccessMessage(Const.Message.CHANGE_PASSWORD_SUCCESS);
        }
        return ServiceResponse.createByErrorMessage(Const.Message.CHANGE_PASSWORD_FAIL);
    }

    /**
     * 更新用户信息
     * @param user
     * @return
     */
    @Override
    public ServiceResponse<User> updateInformation(User user) {
        int resultCount = userMapper.checkEmailByUserId(user.getEmail(), user.getId());
        if (resultCount == 0) {
            return ServiceResponse.createByErrorMessage(Const.Message.EMAIL_EXIST);
        }
        User updateUser = new User();
        updateUser.setId(user.getId());
        updateUser.setEmail(user.getEmail());
        updateUser.setPhone(user.getPhone());
        updateUser.setQuestion(user.getQuestion());
        updateUser.setAnswer(user.getAnswer());

        resultCount = userMapper.updateByPrimaryKeySelective(updateUser);
        if (resultCount == 0) {
            return ServiceResponse.createByErrorMessage(Const.Message.UPDATE_USER_INFO_FAIL);
        }
        return ServiceResponse.createBySuccess(Const.Message.UPDATE_USER_INFO_SUCCESS, updateUser);
    }

    /**
     * 获取登陆用户信息
     * @param id
     * @return
     */
    @Override
    public ServiceResponse<User> getUserInfo(Integer id) {
        User user = userMapper.selectByPrimaryKey(id);
        if (user == null) {
            return ServiceResponse.createByErrorMessage(Const.Message.USER_NOT_EXIST);
        }
        user.setPassword(StringUtils.EMPTY);
        return ServiceResponse.createBySuccess(user);
    }
}
