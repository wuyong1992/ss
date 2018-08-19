package com.zzc.ss.exception;

import com.zzc.ss.common.Const;

/**
 * @author JianGuo
 */
public class JobCategoryNotExistException extends RuntimeException {

    public JobCategoryNotExistException() {
        super(Const.ExceptionMessage.JOB_CATEGORY_NOT_EXIST);
    }
}
