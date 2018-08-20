package com.zzc.ss.service.impl;

import com.google.common.collect.Maps;
import com.zzc.ss.common.Const;
import com.zzc.ss.entity.Manage;
import com.zzc.ss.exception.ManageAccountMismatchingException;
import com.zzc.ss.exception.ManagePasswordMismatchingException;
import com.zzc.ss.repository.ManageRepository;
import com.zzc.ss.service.ManageService;
import com.zzc.ss.utils.MD5Util;
import com.zzc.ss.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author JianGuo
 * on 2018/8/10
 * description:
 */
@Service("manageService")
public class ManageServiceImpl implements ManageService {


    @Autowired
    private ManageRepository manageRepository;


    @Override
    public String login(String account, String password) {
        Manage manage = manageRepository.findByAccount(account);
        if (manage == null) {
            throw new ManageAccountMismatchingException();
        }
        String md5Pawwrod = MD5Util.MD5EncodeUtf8(password);
        // 不区分大小写
        if (!md5Pawwrod.equals(manage.getPassword())) {
            throw new ManagePasswordMismatchingException();
        }

        Map<String, Object> claims = Maps.newHashMap();
        claims.put(Const.ManageAuthJwtTokenClaims.MANAGE_ID, manage.getId());
        String token = null;
        try {
            token = TokenUtil.createJWT(String.valueOf(manage.getId()), claims, manage.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return token;
    }
}
