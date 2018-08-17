package com.zzc.ss.exception;

import com.zzc.ss.common.Const;

/**
 * @author JianGuo
 * on 2018/8/16
 * description:
 */
public class EnterPriseNotExistException extends RuntimeException {

    public EnterPriseNotExistException() {
        super(Const.ExceptionMessage.ENTERPRISE_NOT_EXIST);
    }
}
