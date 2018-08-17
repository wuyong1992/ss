package com.zzc.ss.enums;

/**
 * @author JianGuo
 * on 2018/8/15
 * description:
 */
public enum UserSubscribeEnum {


    /**
     * 用户关注状态
     */
    UN_SUBSCRIBE(0,"未关注"),
    SUBSCRIBE(1,"已关注"),
    ;

    private Integer code;

    private String value;


    public Integer getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }

    UserSubscribeEnum(Integer code, String value) {
        this.code = code;
        this.value = value;
    }


    public static UserSubscribeEnum codeOf(int code) {
        for (UserSubscribeEnum userSubscribeEnum : values()) {
            if (userSubscribeEnum.getCode() == code) {
                return userSubscribeEnum;
            }
        }
        throw new RuntimeException("没找到用户关注状态对应的枚举");
    }




}
