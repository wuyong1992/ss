package com.zzc.ss;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableAsync;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author JianGuo
 * 2018年8月8日20:52:10
 */
@SpringBootApplication
@EnableSwagger2
@EnableAsync
public class SsApplication {

    public static void main(String[] args) {
        SpringApplication.run(SsApplication.class, args);
    }
}
