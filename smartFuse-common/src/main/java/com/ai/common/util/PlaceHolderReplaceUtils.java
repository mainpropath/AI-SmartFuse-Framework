package com.ai.common.util;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @Description: 占位符替换工具类
 */
public class PlaceHolderReplaceUtils {
    private static final Pattern pattern = Pattern.compile("\\{\\{(.*?)\\}\\}");
    private static Matcher matcher;

    /**
     * 替换字符串占位符{{key}}
     *
     * @param sourceString 需要匹配的字符串
     * @param param        参数
     * @return 替换后的字符串
     */
    public static String replaceWithMap(String sourceString, Map<String, Object> param) {
        if (StrUtil.isEmpty(sourceString) || ObjectUtil.isEmpty(pattern)) {
            return sourceString;
        }

        String targetString = sourceString;
        matcher = pattern.matcher(sourceString);
        while (matcher.find()) {
            try {
                String key = matcher.group();
                String keyclone = key.substring(2, key.length() - 2).trim();
                Object value = param.get(keyclone);
                if (value != null) {
                    targetString = targetString.replace(key, value.toString());
                }
            } catch (Exception e) {
                throw new RuntimeException("String formatter failed", e);
            }
        }
        return targetString;
    }

    public static Set<String> findPlaceHolderKeys(String sourceString) {
        return findPlaceHolderKeys(sourceString, PlaceHolderReplaceUtils.pattern);
    }

    /**
     * 查找String中的占位符keys
     *
     * @param sourceString 需要匹配的字符串
     * @param pattern      表达式
     * @return 占位符集合
     */
    public static Set<String> findPlaceHolderKeys(String sourceString, Pattern pattern) {
        Set<String> placeHolderSet = new HashSet<>();
        if (StrUtil.isEmpty(sourceString) || ObjectUtil.isEmpty(pattern)) {
            return placeHolderSet;
        }

        matcher = pattern.matcher(sourceString);
        while (matcher.find()) {
            String key = matcher.group();
            String placeHolder = key.substring(1, key.length() - 1).trim();
            placeHolderSet.add(placeHolder);
        }

        return placeHolderSet;
    }

}

