package com.example.tiny_demo.common.exception;

import com.example.tiny_demo.common.api.IErrorCode;

/**
 * 用户触发自定义API异常
 */
public class Asserts {

    public static ApiException fail(String message) {
        throw new ApiException(message);
    }

    public static ApiException fail(IErrorCode errorCode) {
        throw  new ApiException(errorCode);
    }
}
