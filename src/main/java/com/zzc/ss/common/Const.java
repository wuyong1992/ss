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
        public static final String TOKEN_CREATE_ERROR = "TOKEN生成异常";
        public static final String LOGIN_SUCCESS = "登录成功";
        public static final String TOKEN_EXPIRE = "TOKEN过期或失效";
        public static final String TOKEN_VALID = "有效TOKEN";
    }

    /**
     * user jwt token claims 中的key值
     */
    public interface UserAuthJwtTokenClaims {
        public static final String USER_ID = "user_id";
        public static final String OPENID = "openid";
        public static final String UNIONID = "unionid";
        public static final String PHONE = "phone";
        public static final String ENTERPRISE_ID = "enterpriseId";
    }

    /**
     * manage jwt token claims 中的key值
     */
    public interface ManageAuthJwtTokenClaims {
        public static final String MANAGE_ID = "user_id";
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
        public static final String JOB_CATEGORY_NOT_EXIST = "当前招聘分类不存在";
        public static final String ACCOUNT_MISMATCHING = "账号不匹配";
        public static final String PASSWORD_MISMATCHING = "密码不匹配";
    }

}
