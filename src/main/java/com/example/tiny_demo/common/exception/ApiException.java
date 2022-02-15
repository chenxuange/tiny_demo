package com.example.tiny_demo.common.exception;

import com.example.tiny_demo.common.api.IErrorCode;

/**
 * 自定义API异常，方便再service层中区分异常业务状态
 */
public class ApiException extends RuntimeException{
    private IErrorCode errorCode;

    public ApiException(String message) {
        super(message);
    }

    public ApiException(IErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public IErrorCode getErrorCode() {
        return errorCode;
    }
}
