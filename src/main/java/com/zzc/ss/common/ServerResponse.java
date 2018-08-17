package com.zzc.ss.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 2018年8月8日20:56:00
 * description: 通用返回对象
 * T 泛型的好处
 *
 * @author JianGuo
 */

@Getter
@Setter
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@ApiModel(description = "统一返回对象")
public class ServerResponse<T> implements Serializable {

    @ApiModelProperty(value = "自定义状态，区别于HTTP状态码，用来定义自己的状态码")
    private Integer code;

    @ApiModelProperty(value = "返回提示信息")
    private String msg;

    @ApiModelProperty(value = "具体的返回的数据")
    private T data;

    private ServerResponse(Integer code) {
        this.code = code;
    }

    private ServerResponse(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private ServerResponse(Integer code, T data) {
        this.code = code;
        this.data = data;
    }

    private ServerResponse(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    @JsonIgnore
    public boolean isSuccess() {
        return this.code.equals(ResponseCode.SUCCESS.getCode());
    }

    public static ServerResponse createBySuccess() {
        return new ServerResponse(ResponseCode.SUCCESS.getCode());
    }

    public static ServerResponse createBySuccessMsg(String msg) {
        return new ServerResponse(ResponseCode.SUCCESS.getCode(), msg);
    }

    public static <T> ServerResponse<T> createBySuccess(T data) {
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode(), data);
    }

    public static <T> ServerResponse<T> createBySuccess(String msg, T data) {
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode(), msg, data);
    }

    public static ServerResponse createByError() {
        return new ServerResponse(ResponseCode.ERROR.getCode());
    }

    public static ServerResponse createByErrorMsg(String msg) {
        return new ServerResponse(ResponseCode.ERROR.getCode(), msg);
    }

    public static ServerResponse createByErrorCodeMsg(Integer code, String msg) {
        return new ServerResponse(code, msg);
    }


}
