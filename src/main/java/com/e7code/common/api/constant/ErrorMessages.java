package com.e7code.common.api.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ssr on 2017/3/22.
 */
public class ErrorMessages extends ErrorCodes {
    private static Map<Integer, String> msgMapCn = new HashMap<Integer, String>() {{
        put(PARAM_INVALID, "参数错误!");
        put(SYSTEM_EXCEPTION, "系统异常!");
        put(PROPERTY_NOT_FOUND, "属性不存在!");
        put(PROPERTY_COPY_ERROR, "属性复制错误!");
        put(DATA_NOT_FOUND, "数据不存在或已删除!");
        put(DATA_OPTIMISTIC_LOCKING_FAILURE, "数据已被其他人修改!");
    }};

    public static String getMsg(Integer code) {
        return getMsgMap("cn").get(code);
    }

    public static String getMsg(Integer code, String... params) {
        String msg = getMsg(code);
        if(msg == null || params == null || params.length == 0) {
            return msg;
        }

        for (String param : params) {
            msg = msg.replaceFirst("\\{\\}", param);
        }

        return msg;
    }

    private static Map<Integer, String> getMsgMap(String language) {
        //TODO:
        return msgMapCn;
    }
}
