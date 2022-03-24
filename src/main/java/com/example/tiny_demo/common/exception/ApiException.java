package com.example.tiny_demo.common.exception;

import com.example.tiny_demo.common.api.IErrorCode;

/**
 * 自定义API异常，方便在service层中区分异常业务状态，直接抛出异常
 */
public class ApiException extends RuntimeException{
    private IErrorCode errorCode;

    // 利用提示信息构造api异常
    public ApiException(String message) {
        super(message);
    }

    // 利用错误码构造API异常
    public ApiException(IErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public IErrorCode getErrorCode() {
        return errorCode;
    }
}
