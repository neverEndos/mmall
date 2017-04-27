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

    public interface Role{
        int ROLE_COSTOMER = 0;// 普通用户
        int ROLE_ADMIN = 1;// 管理员
    }
}
