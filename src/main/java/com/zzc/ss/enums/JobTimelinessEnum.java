package com.zzc.ss.enums;

/**
 * @author JianGuo
 * on 2018/8/14
 * description:
 */
public enum JobTimelinessEnum {


    /**
     * 招聘信息状态
     */
    SHORT(1, "短期"),
    LONG(2, "长期"),
    ;

    private Integer code;

    private String value;


    public Integer getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }

    JobTimelinessEnum(Integer code, String value) {
        this.code = code;
        this.value = value;
    }


    public static JobTimelinessEnum codeOf(int code) {
        for (JobTimelinessEnum jobStatusEnum : values()) {
            if (jobStatusEnum.getCode() == code) {
                return jobStatusEnum;
            }
        }
        throw new RuntimeException("没找到招聘信息状态对应的枚举");
    }


}
