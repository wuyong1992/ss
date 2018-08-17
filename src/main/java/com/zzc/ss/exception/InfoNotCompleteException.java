package com.zzc.ss.exception;

import com.zzc.ss.common.Const;

/**
 * @author JianGuo
 * on 2018/8/17
 * description:
 */
public class InfoNotCompleteException extends RuntimeException {

    public InfoNotCompleteException() {
        super(Const.ExceptionMessage.INFO_NOT_COMPLETE);
    }
}
