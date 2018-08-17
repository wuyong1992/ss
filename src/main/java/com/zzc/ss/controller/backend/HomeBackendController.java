package com.zzc.ss.controller.backend;

import io.swagger.annotations.Api;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author JianGuo
 * on 2018/8/10
 * description:
 */
@RestController
@RequestMapping("backend/home")
@Validated
@Api(description = "首页后端接口")
public class HomeBackendController {
}
