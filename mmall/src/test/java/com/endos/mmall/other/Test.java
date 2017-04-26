package com.endos.mmall.other;

import com.endos.mmall.common.Const;
import org.springframework.util.DigestUtils;

/**
 * Created by Endos on 2017/04/26.
 */
public class Test {
    public static void main(String[] args) {
        String password = "123456";
        String md5 = DigestUtils.md5DigestAsHex((password + Const.MD5_SLAT).getBytes()).toUpperCase();
        System.out.println(md5);
    }
}
