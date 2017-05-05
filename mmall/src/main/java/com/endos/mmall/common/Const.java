package com.endos.mmall.common;

/**
 * 常量类
 * Created by Endos on 2017/04/26.
 */
public class Const {
    /* session中存放用户登录信息key */
    public static final String CURRENT_USER = "currentUser";

    /* MD5盐值 */
    public static final String MD5_SLAT = "Endos";

    public static final String USERNAME = "username";
    public static final String EMAIL = "email";

    public interface Message{
        String LOGIN_SUCCESS = "登陆成功";
        String REGISTER_SUCCESS = "注册成功";
        String VALID_SUCCESS = "验证成功";
        String CHANGE_PASSWORD_SUCCESS = "密码修改成功";
        String UPDATE_USER_INFO_SUCCESS = "更新信息成功";
        String ADD_CATEGORY_SUCCESS = "增加品类成功";
        String UPDATE_CATEGORY_SUCCESS = "更新品类成功";

        String USER_EXIST = "用户名已存在";
        String EMAIL_EXIST = "邮箱已存在";

        String USER_NOT_EXIST = "用户名不存在";

        String USER_NOT_LOGIN = "用户未登陆";

        String ANSWER_WRONG = "问题的答案错误";
        String PASSWORD_WRONG = "密码错误";
        String TOKEN_WRONG = "token错误,请重新获取重置密码的token";

        String REGISTER_FAIL = "注册失败";
        String CHANGE_PASSWORD_FAIL = "密码修改失败";
        String UPDATE_USER_INFO_FAIL = "更新个人信息失败";
        String ADD_CATEGORY_FAIL = "增加品类失败";
        String UPDATE_CATEGORY_FAIL = "更新品类失败";

        String EMPTY_PARAM = "参数不能为空";
        String EMPTY_QUESTION = "找回密码的问题是空的";
        String EMPTY_TOKEN = "token不能为空";

        String TOKEN_INVALID = "token无效或已过期";

        String NEED_LOGIN = "未登录,需要强制登录status=10";

        String NOT_ADMIN = "不是管理员,无法登录";
    }

    public interface Role{
        int ROLE_COSTOMER = 0;// 普通用户
        int ROLE_ADMIN = 1;// 管理员
    }
}
