package com.snowy.common.net;

import android.text.TextUtils;

import java.util.HashMap;

/**
 * @author guobaolun
 */
public class CodeUtils {

    private static HashMap<Integer, String> map = new HashMap();


    public static final int OK = 200;

    static {
        map.put(5000, "服务器出错");
        map.put(5001, "用户名不能为空");
        map.put(5002, "密码不能为空");
        map.put(5003, "用户不存在");
        map.put(5004, "用户名或密码错误");
        map.put(5005, "手机号不正确");
        map.put(5006, "手机号已被注册");
        map.put(5007, "验证码错误");
        map.put(5008, "token失效,请重新登录");
        map.put(5009, "字符串长度超出范围");
        map.put(5010, "数据值错误");

    }

    public static String getMassage(int code) {
        String message = map.get(code);
        return TextUtils.isEmpty(message) ? map.get(5000) : message;
    }

}
