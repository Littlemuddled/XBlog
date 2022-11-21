package com.kun;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author kun
 * @since 2022-11-21 19:11
 */
@SpringBootApplication
@MapperScan("com.kun.mapper")
public class XBlogAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(XBlogAdminApplication.class,args);
    }

}
