package com.zzc.ss.enums;

/**
 * @author JianGuo
 * on 2018/8/10
 * description:
 */
public enum JobPayPeriodEnum {

    /**
     * 招聘工资结算周期
     */
    DAY(1,"日结"),
    WEEK(2,"周结"),
    MONTH(3,"月结"),
    HOUR(4,"小时"),
    ;

    private Integer code;

    private String value;


    public Integer getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }

    JobPayPeriodEnum(Integer code, String value) {
        this.code = code;
        this.value = value;
    }


    public static JobPayPeriodEnum codeOf(int code) {
        for (JobPayPeriodEnum jobPayPeriodEnum : values()) {
            if (jobPayPeriodEnum.getCode() == code) {
                return jobPayPeriodEnum;
            }
        }
        throw new RuntimeException("没找到招聘工资结算周期对应的枚举");
    }
}
