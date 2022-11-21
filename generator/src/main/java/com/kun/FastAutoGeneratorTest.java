package com.kun;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Desc:
 * User:Administrator
 * Date:2022-11-18 19:49
 */
public class FastAutoGeneratorTest {

    public static void main(String[] args) {
        List<String> tableNameList = new ArrayList<>();
        tableNameList.add("tb_article_tag");
        tableNameList.add("tb_category");
        tableNameList.add("tb_comment");
        tableNameList.add("tb_link");
        tableNameList.add("tb_tag");

        FastAutoGenerator.create("jdbc:mysql://127.0.0.1:3306/db_blog?characterEncoding=utf-8&userSSL=false", "root", "666666")
                        .globalConfig(builder -> {
                            builder.author("kun") // 设置作者
                                    //.enableSwagger() // 开启 swagger 模式
                                    .fileOverride() // 覆盖已生成文件
                                    .outputDir("D://mybatis_plus"); // 指定输出目录
                        })
                        .packageConfig(builder -> {
                            builder.parent("com.kun") // 设置父包名
                                    .moduleName("blog") // 设置父包模块名
                                    .pathInfo(Collections.singletonMap(OutputFile.xml, "D://mybatis_plus"));// 设置mapperXml生成路径
                        })
                        .strategyConfig(builder -> {
                            builder.addInclude(tableNameList) // 设置需要生成的表名
                                    .addTablePrefix("tb_"); // 设置过滤表前缀
                        })
                        .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker 引擎模板，默认的是Velocity引擎模板
                        .execute();
    }
}
