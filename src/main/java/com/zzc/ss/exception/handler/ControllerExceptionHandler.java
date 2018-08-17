package com.zzc.ss.exception.handler;

import com.zzc.ss.common.ServerResponse;
import com.zzc.ss.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author JianGuo
 * on 2018/8/15
 * description:
 */
@RestControllerAdvice
public class ControllerExceptionHandler {


    /**
     * token 创建异常
     */
    @ExceptionHandler(TokenCreateException.class)
    @ResponseStatus(HttpStatus.OK)
    public ServerResponse handlerTokenCreateException(TokenCreateException ex) {
        return ServerResponse.createByErrorMsg(ex.getMessage());
    }

    /**
     * job info不存在异常
     */
    @ExceptionHandler(JobNotExistException.class)
    @ResponseStatus(HttpStatus.OK)
    public ServerResponse handlerJobNotExistException(JobNotExistException ex) {
        return ServerResponse.createByErrorMsg(ex.getMessage());
    }


    /**
     * 当前工作处于申请中，需等待结果
     */
    @ExceptionHandler(JobInApplyingException.class)
    @ResponseStatus(HttpStatus.OK)
    public ServerResponse handlerJobInApplyingException(JobInApplyingException ex) {
        return ServerResponse.createByErrorMsg(ex.getMessage());
    }

    /**
     * user info 不存在异常
     */
    @ExceptionHandler(UserNotExistException.class)
    @ResponseStatus(HttpStatus.OK)
    public ServerResponse handlerUserNotExistException(UserNotExistException ex) {
        return ServerResponse.createByErrorMsg(ex.getMessage());
    }

    /**
     * user job 信息不存在异常
     */
    @ExceptionHandler(UserJobNotExistException.class)
    @ResponseStatus(HttpStatus.OK)
    public ServerResponse handlerUserJobNotExistException(UserJobNotExistException ex) {
        return ServerResponse.createByErrorMsg(ex.getMessage());
    }

    /**
     * user job 不处于申请中异常
     */
    @ExceptionHandler(UserJobNotInApplyingException.class)
    @ResponseStatus(HttpStatus.OK)
    public ServerResponse handlerUserJobNotInApplyingException(UserJobNotInApplyingException ex) {
        return ServerResponse.createByErrorMsg(ex.getMessage());
    }

    /**
     * 用户或者企业信息不完善
     */
    @ExceptionHandler(InfoNotCompleteException.class)
    @ResponseStatus(HttpStatus.OK)
    public ServerResponse handlerInfoNotCompleteException(InfoNotCompleteException ex) {
        return ServerResponse.createByErrorMsg(ex.getMessage());
    }


}
