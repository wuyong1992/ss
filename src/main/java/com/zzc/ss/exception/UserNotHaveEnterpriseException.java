package com.zzc.ss.exception;

import com.zzc.ss.common.Const;

/**
 * @author JianGuo
 * on 2018/8/22
 * description:
 */
public class UserNotHaveEnterpriseException extends RuntimeException {
    public UserNotHaveEnterpriseException() {
        super(Const.ExceptionMessage.USER_NOT_HAVE_ENTERPRISE);
    }
}
