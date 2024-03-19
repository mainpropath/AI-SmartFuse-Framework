package com.ai.domain.service.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 此注解用来标注出用户存储器当中的存储ID，支持 String\int\long\Integer\Long作为MemoryId
 * 此注解生效的前提是：设置了消息存储器
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER})
public @interface MemoryId {
}
