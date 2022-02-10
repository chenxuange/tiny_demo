package com.example.tiny_demo.common.api;

/**
 * 错误码枚举类
 */
public enum ResultCode implements IErrorCode{
    SUCCESS(200, "valerius恭喜您操作成功"),
    FAILED(500, "请联系管理员valerius");
    private long code;
    private String message;
    private ResultCode(long code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public long getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
