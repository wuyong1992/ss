package com.zzc.ss.exception;

/**
 * @author JianGuo
 * on 2018/8/15
 * description:
 */
public class JobInApplyingException extends RuntimeException {

    public JobInApplyingException() {
        super("当前工作处于申请中，请耐心等待");
    }
}
