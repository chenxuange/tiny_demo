package com.example.tiny_demo.common.api;

import lombok.Data;

/**
 * 通用返回对象
 * @param <T>
 */
@Data
public class CommonResult<T> {
    private long code;
    private String message;
    private T data;

    public CommonResult(long code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    // 默认成功返回
    public static <T> CommonResult<T> success(T data) {
        return new CommonResult<T>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), data);
    }

    // 自定义失败返回
    public static <T> CommonResult<T> fail(long code, String message, T data) {
        return new CommonResult<T>(code, message, data);
    }

    // 自定义失败返回提示
    public static <T> CommonResult<T> fail(String message, T data) {
        return new CommonResult<T>(ResultCode.FAILED.getCode(), message, data);
    }

    // 默认失败返回
    public static <T> CommonResult<T> fail(T data) {
        return new CommonResult<T>(ResultCode.FAILED.getCode(), ResultCode.FAILED.getMessage(), data);
    }

    // 失败返回错误码
    public static <T> CommonResult<T> fail(IErrorCode errorCode, T data) {
        return new CommonResult<>(errorCode.getCode(), errorCode.getMessage(), data);
    }

    // 校验失败返回
    public static <T> CommonResult<T> validateFailed(String message, T data) {
        return new CommonResult<>(ResultCode.VALIDATE_FAILED.getCode(), message, data);
    }
}
