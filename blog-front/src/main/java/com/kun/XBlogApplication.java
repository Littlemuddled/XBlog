package com.kun;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Desc:
 * User:Administrator
 * Date:2022-11-18 16:39
 */
@SpringBootApplication
@MapperScan("com.kun.mapper")
public class XBlogApplication {

    public static void main(String[] args) {
        SpringApplication.run(XBlogApplication.class,args);
    }
}
