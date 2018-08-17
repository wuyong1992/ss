package com.zzc.ss.exception;

/**
 * @author JianGuo
 * on 2018/8/16
 * description:
 */
public class UserJobNotInApplyingException extends RuntimeException {

    public UserJobNotInApplyingException() {
        super("当前工作不处于待审核状态");
    }
}
