package com.zzc.ss.enums;

/**
 * @author JianGuo
 * on 2018/8/14
 * description:
 */
public enum UserApplyJobStatusEnum {


    /**
     * 用户申请工作状态
     */
    IN_APPLYING(1, "审核中"),
    APPLY_PASS(2, "审核通过"),
    APPLE_FAIL(3, "审核未通过"),
    ;

    private Integer code;

    private String value;


    public Integer getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }

    UserApplyJobStatusEnum(Integer code, String value) {
        this.code = code;
        this.value = value;
    }


    public static UserApplyJobStatusEnum codeOf(int code) {
        for (UserApplyJobStatusEnum jobStatusEnum : values()) {
            if (jobStatusEnum.getCode() == code) {
                return jobStatusEnum;
            }
        }
        throw new RuntimeException("没找到用户申请工作状态对应的枚举");
    }


}
