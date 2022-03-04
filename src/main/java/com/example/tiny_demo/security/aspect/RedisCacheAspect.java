package com.example.tiny_demo.security.aspect;

import com.example.tiny_demo.security.anotation.CustomCacheException;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * Redis缓存切面，防止Redis宕机影响正常业务逻辑
 * Created by macro on 2020/3/17.
 */
@Aspect
@Component
@Order(2)
public class RedisCacheAspect {
    private static final Logger logger = LoggerFactory.getLogger(RedisCacheAspect.class);

    private static boolean skipRedisCache = false;

    // com.example.tiny_demo.modules.ums.service.impl
    @Pointcut("execution(public * com.example.tiny_demo.modules..service..*CacheService.*(..))")
    public void cacheAspect() {
    }

    @Around("cacheAspect()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        // 连接点执行的类
        Class<?> curInvocationClass = joinPoint.getTarget().getClass();
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        Object result = null;
        try {
            logger.debug("cache related  intercept");
            // 新增跳过redis缓存
            if(skipRedisCache) {
                logger.debug("current skip RedisCache");
                return null;
            }
            result = joinPoint.proceed();
        } catch (RedisConnectionFailureException redisConnectionException){
            //  若启动应用后第一次使用redis缓存就出现 RedisConnectionException，说明了redis服务没启动，则以后的每次缓存请求都直接跳过，即停止每次尝试连接redis
            // TODO 此外，应设置一个定时任务，多长时间后恢复尝试redis连接,即将skipRedisCache设为false即可
            skipRedisCache = true;
            logger.warn(redisConnectionException.getMessage());
            logger.warn("if firstly Unable to connect to Redis, the rest of cache requests skip");
        } catch (Throwable throwable) {
            //有CacheException注解的类打印异常就可
            if(curInvocationClass.isAnnotationPresent(CustomCacheException.class)) {
                logger.debug("redis related while class with CustomCacheException");
            }
            //有CacheException注解的方法, 实际上后续都是在cache类上加注解，不在cache类方法上加注解
            else if (method.isAnnotationPresent(CustomCacheException.class)) {
                logger.debug("redis related  while method with CustomCacheException");
                // 此处不抛出异常，否则影响非redis的正常功能
//                throw throwable;
            } else {
                logger.debug("other exception");
            }
            // 打印以下异常即可，跳过redis继续执行其他
            logger.warn(throwable.getMessage());
        }
        return result;
    }

}
