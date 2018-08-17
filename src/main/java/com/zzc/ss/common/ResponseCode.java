package com.zzc.ss.common;

/**
 * 2018年8月8日20:56:17
 * description: 自定义的响应状态码
 * @author JianGuo
 */
public enum ResponseCode {
    /**
     * 字段
     */
    SUCCESS(0, "SUCCESS"),
    ERROR(1, "ERROR"),
    ILLEGAL_ARGUMENT(2, "ILLEGAL_ARGUMENT"),
    NEED_LOGIN(10, "NEED_LOGIN"),
    TOKEN_ERROR(20, "token无效"),
    TOKEN_EXPIRE(201, "TOKEN 过期"),
    TOKEN_ILLEGAL_ARG(202, "TOKEN 参数异常"),
    ;
    private final Integer code;
    private final String desc;

    ResponseCode(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
