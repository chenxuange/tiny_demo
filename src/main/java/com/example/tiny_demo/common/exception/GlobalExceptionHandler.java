package com.example.tiny_demo.common.exception;

import com.example.tiny_demo.common.api.CommonResult;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理
 * 校验异常的三种情况：
 *
 * 1. 单一参数校验异常是 ConstraintViolationException
 * 实际post请求有两种方式，一种是基于form-data格式的数据传递，另外一种就是基于json格式的数据传递，两种传递方式引发的异常也是不一样的
 * 2. form-data 表单形式提交， 提交的不是json格式，而是多个属性键值对，不需要@RequestBody来转换
 * 出现异常是 BindException
 * 3. json格式提交, 则需要@RequestBody将json格式字符串转换为javaBean
 * 出现的异常时 MethodArgumentNotValidException
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 处理入参校验异常， 暂时没有采用form-data格式，而是json,只用写一种对应方式
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public CommonResult handleBindException(MethodArgumentNotValidException e) {
        String message = null;
        BindingResult result = e.getBindingResult();
        if(result.hasErrors()) {
            FieldError fieldError = result.getFieldError();
            if (fieldError != null) {
                message = fieldError.getField() + ": " + fieldError.getDefaultMessage();
            }
        }
        return CommonResult.validateFailed(message, null);
    }

    // 处理所有异常
    @ExceptionHandler(Exception.class)
    public CommonResult handleAll(Exception e) {
        System.out.println("handleAll "+ e.getClass().getName());
        return CommonResult.fail(e.getMessage());
    }
}
