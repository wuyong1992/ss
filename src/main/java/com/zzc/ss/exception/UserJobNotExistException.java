package com.zzc.ss.exception;

import com.zzc.ss.common.Const;

/**
 * @author JianGuo
 * on 2018/8/16
 * description:
 */
public class UserJobNotExistException extends RuntimeException {

    public UserJobNotExistException() {
        super(Const.ExceptionMessage.USER_JOB_NOT_EXIST);
    }
}
