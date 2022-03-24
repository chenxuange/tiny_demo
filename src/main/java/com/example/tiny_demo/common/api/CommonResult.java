package com.example.tiny_demo.common.api;

import lombok.Data;

/**
 * 通用返回对象
 */
@Data
public class CommonResult<T> {
    // 状态码
    private long code;
    // 提示消息
    private String message;
    // 返回数据
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

    // 可定义完整信息的失败返回
    public static <T> CommonResult<T> fail(long code, String message, T data) {
        return new CommonResult<T>(code, message, data);
    }

    // 可自定义提示信息的失败返回
    public static <T> CommonResult<T> fail(String message, T data) {
        return new CommonResult<T>(ResultCode.FAILED.getCode(), message, data);
    }

    // 默认失败返回
    public static <T> CommonResult<T> fail(T data) {
        return fail(ResultCode.FAILED, data);
    }

    // 带错误码的失败返回
    public static <T> CommonResult<T> fail(IErrorCode errorCode, T data) {
        return new CommonResult<>(errorCode.getCode(), errorCode.getMessage(), data);
    }

    // 校验失败
    public static <T> CommonResult<T> validateFailed(String message, T data) {
        return new CommonResult<>(ResultCode.VALIDATE_FAILED.getCode(), message, data);
    }

    // 没有授权拒绝
    public static <T> CommonResult<T> unauthorized( T data) {
        return fail(ResultCode.UNAUTHORIZED, data);
    }

    // 认证失败拒绝
    public static <T> CommonResult<T> unauthenticated(T data) {
        return fail(ResultCode.UNAUTHENTICATED, data);

    }
}
