package com.zzc.ss.exception;

import com.zzc.ss.common.Const;

/**
 * @author JianGuo
 * on 2018/8/20
 * description:
 */
public class ManageAccountMismatchingException extends RuntimeException {

    public ManageAccountMismatchingException() {
        super(Const.ExceptionMessage.ACCOUNT_MISMATCHING);
    }
}
