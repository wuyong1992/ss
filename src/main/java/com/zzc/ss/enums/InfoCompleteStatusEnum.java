package com.zzc.ss.enums;

/**
 * @author JianGuo
 * on 2018/8/17
 * description:
 */
public enum InfoCompleteStatusEnum {

    /**
     * 信息是否完整
     */
    YES(0, "否"),
    NO(1, "是"),
    ;

    private Integer code;

    private String value;


    public Integer getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }

    InfoCompleteStatusEnum(Integer code, String value) {
        this.code = code;
        this.value = value;
    }


    public static InfoCompleteStatusEnum codeOf(int code) {
        for (InfoCompleteStatusEnum infoCompleteStatus : values()) {
            if (infoCompleteStatus.getCode() == code) {
                return infoCompleteStatus;
            }
        }
        throw new RuntimeException("没找到招聘信息状态对应的枚举");
    }



}
