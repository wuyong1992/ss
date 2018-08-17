package com.zzc.ss.service.impl;

import com.zzc.ss.repository.ManageRepository;
import com.zzc.ss.service.ManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author JianGuo
 * on 2018/8/10
 * description:
 */
@Service("manageService")
public class ManageServiceImpl implements ManageService {


    @Autowired
    private ManageRepository manageRepository;


}
