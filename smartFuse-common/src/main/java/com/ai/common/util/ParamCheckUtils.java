package com.ai.common.util;

import cn.hutool.core.util.StrUtil;

import java.util.List;

/**
 * @Description: 各种异常状态判断
 **/
public class ParamCheckUtils {

    public static void checkStr(String str, String message) {
        if (StrUtil.isEmpty(str)) throw new RuntimeException(message);
    }

    public static void checkList(List list, String message) {
        if (list == null || list.isEmpty()) throw new RuntimeException(message);
    }

    public static void checkObject(Object object, String message) {
        if (object == null) throw new RuntimeException(message);
    }

}
