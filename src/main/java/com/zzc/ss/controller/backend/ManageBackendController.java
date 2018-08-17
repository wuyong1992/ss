package com.zzc.ss.controller.backend;

import com.zzc.ss.service.ManageService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author JianGuo
 * on 2018/8/10
 * description:
 * todo 暂时不动
 */
@RestController
@RequestMapping("backend/manage")
@Validated
@Api(description = "管理员后端接口")
public class ManageBackendController {

    @Autowired
    private ManageService manageService;
    
}
