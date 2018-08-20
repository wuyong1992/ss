package com.zzc.ss.exception;

import com.zzc.ss.common.Const;

/**
 * @author JianGuo
 * on 2018/8/20
 * description:
 */
public class ManagePasswordMismatchingException extends RuntimeException {

    public ManagePasswordMismatchingException() {
        super(Const.ExceptionMessage.PASSWORD_MISMATCHING);
    }
}
