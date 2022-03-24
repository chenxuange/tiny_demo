package com.example.tiny_demo.security.anotation;


import java.lang.annotation.*;

/**
 * 自定义缓存异常注解 CustomCacheException，
 * 有该注解的缓存类或方法会抛出异常
 */
@Documented
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface CustomCacheException {
}
