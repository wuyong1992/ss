package com.zzc.ss.common;

import com.zzc.ss.vo.JobVO;

/**
 * @author JianGuo
 * on 2018/8/8
 * description:
 */
public class Const {


    /**
     * 操作执行的结果常量
     */
    public interface ExecuteResultMessage {

        public static final String SAVE_SUCCESS = "保存成功";
        public static final String SAVE_FAIL = "保存失败";
        public static final String APPLY_JOB_SUBMIT_SUCCESS = "工作申请已提交，请保持手机号畅通，耐心等待工作人员联系";
        public static final String QUERY_SUCCESS = "查询成功";
        public static final String OPERATE_SUCCESS = "操作成功";
        public static final String APPLY_ENTERPRISE_SUBMIT_SUCCESS = "企业申请已提交，请耐心等待工作人员联系";
    }

    /**
     * jwt token claims 中的key值
     */
    public interface AuthJwtTokenClaims {
        public static final String USER_ID = "user_id";
        public static final String OPENID = "openid";
        public static final String UNIONID = "unionid";
        public static final String PHONE = "phone";
        public static final String ENTERPRISE_ID = "enterpriseId";
    }

    /**
     * 异常返回信息常量
     */
    public interface ExceptionMessage {
        public static final String TOKEN_CREATE_FAIL = "token创建失败";
        public static final String JOB_NOT_EXIST = "工作不存在";
        public static final String USER_NOT_EXIST = "当前用户不存在";
        public static final String USER_JOB_NOT_EXIST = "当前求职信息不存在";
        public static final String ENTERPRISE_NOT_EXIST = "当前企业不存在";
        public static final String INFO_NOT_COMPLETE = "信息不完善";
    }

}
