package com.endos.mmall.util;

import com.endos.mmall.common.Const;
import org.springframework.util.DigestUtils;

/**
 * Created by Endos on 2017/04/26.
 */
public class MD5Util {

    // MD5加密
    public static String getMD5(String origin) {
        return DigestUtils.md5DigestAsHex((origin + Const.MD5_SLAT).getBytes());
    }
}
