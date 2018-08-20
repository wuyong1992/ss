package com.zzc.ss.service;

/**
 * @author JianGuo
 * on 2018/8/10
 * description:
 */
public interface ManageService {
    /**
     * 管理员登录
     * @param account
     * @param password
     */
    String login(String account, String password);
}
