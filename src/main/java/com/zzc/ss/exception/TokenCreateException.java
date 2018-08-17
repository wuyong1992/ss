package com.zzc.ss.exception;

import com.zzc.ss.common.Const;

/**
 * @author JianGuo
 * on 2018/8/15
 * description:
 */
public class TokenCreateException extends RuntimeException {

    public TokenCreateException() {
        super(Const.ExceptionMessage.TOKEN_CREATE_FAIL);
    }
}
