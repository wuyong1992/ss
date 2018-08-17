package com.zzc.ss.enums;

/**
 * @author JianGuo
 * on 2018/8/14
 * description:
 */
public enum EnterpriseInfoStatusEnum {

    /**
     * 企业状态
     */
    IN_APPLYING(1,"审核中"),
    APPLY_PASS(2,"审核通过"),
    APPLY_FAIL(3,"审核未通过"),
    FORBIDDEN(4,"禁用"),
    ;

    private Integer code;

    private String value;


    public Integer getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }

    EnterpriseInfoStatusEnum(Integer code, String value) {
        this.code = code;
        this.value = value;
    }


    public static EnterpriseInfoStatusEnum codeOf(int code) {
        for (EnterpriseInfoStatusEnum enterpriseInfoStatusEnum : values()) {
            if (enterpriseInfoStatusEnum.getCode() == code) {
                return enterpriseInfoStatusEnum;
            }
        }
        throw new RuntimeException("没找到企业状态对应的枚举");
    }



}
