package com.kun;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Desc:
 * User:Administrator
 * Date:2022-11-18 16:39
 */
@SpringBootApplication
@MapperScan("com.kun.mapper")
@EnableScheduling
@EnableSwagger2
public class XBlogApplication {

    public static void main(String[] args) {
        SpringApplication.run(XBlogApplication.class,args);
    }
}
