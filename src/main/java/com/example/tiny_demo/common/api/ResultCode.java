package com.example.tiny_demo.common.api;

/**
 * 错误码枚举类
 */
public enum ResultCode implements IErrorCode{
    SUCCESS(200, "恭喜您操作成功"),
    FAILED(500, "操作失败，请联系valerius"),
    // 自定义错误操作码
    ADMIN_EXIST_ERROR(600, "当前用户名已经注册"),
    USERNAME_OR_PASSWORD_ERROR(601, "用户名或密码错误"),
    VALIDATE_FAILED(400, "参数检验失败");
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
