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

    // 默认失败返回
    public static <T> CommonResult<T> fail(T data) {
        return new CommonResult<T>(ResultCode.FAILED.getCode(), ResultCode.FAILED.getMessage(), data);
    }
}
