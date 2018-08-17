package com.zzc.ss.enums;

/**
 * @author JianGuo
 * on 2018/8/14
 * description:
 */
public enum JobCategoryStatusEnum {


    /**
     * 工作分类的状态
     */
    FORBIDDEN(0,"禁用"),
    ENABLED(1,"启用"),
    ;

    private Integer code;

    private String value;


    public Integer getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }

    JobCategoryStatusEnum(Integer code, String value) {
        this.code = code;
        this.value = value;
    }

    public static JobCategoryStatusEnum codeOf(int code) {
        for (JobCategoryStatusEnum jobCategoryStatusEnum : values()) {
            if (jobCategoryStatusEnum.getCode() == code) {
                return jobCategoryStatusEnum;
            }
        }
        throw new RuntimeException("没找到工作分类的状态对应的枚举");
    }

}
