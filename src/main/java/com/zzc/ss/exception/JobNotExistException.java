package com.zzc.ss.exception;

import com.zzc.ss.common.Const;

/**
 * @author JianGuo
 * on 2018/8/15
 * description:
 */
public class JobNotExistException extends RuntimeException {

    public JobNotExistException() {
        super(Const.ExceptionMessage.JOB_NOT_EXIST);
    }
}
