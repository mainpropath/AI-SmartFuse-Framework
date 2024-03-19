package com.ai.domain.service.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 此注解用来表示系统消息或用户消息当中的占位符内容，如果占位符在系统消息和用户消息当中都存在，那么都会替换。
 */
@Target(PARAMETER)
@Retention(RUNTIME)
public @interface V {
    String value();
}
