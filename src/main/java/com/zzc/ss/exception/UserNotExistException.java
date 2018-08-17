package com.zzc.ss.exception;

import com.zzc.ss.common.Const;

/**
 * @author JianGuo
 * on 2018/8/15
 * description:
 */
public class UserNotExistException extends RuntimeException {


    public UserNotExistException() {
        super(Const.ExceptionMessage.USER_NOT_EXIST);
    }
}
