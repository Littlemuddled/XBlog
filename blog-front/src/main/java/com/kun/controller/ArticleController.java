package com.kun.controller;

import com.kun.domain.Result;
import com.kun.service.IArticleService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Desc:
 * User:Administrator
 * Date:2022-11-18 19:32
 */
@RestController
@RequestMapping("/article")
public class ArticleController {

    @Resource
    private IArticleService articleService;

    @GetMapping("/hotArticleList")
    public Result hotArticleList() {
        return articleService.hotArticleList();
    }

    @GetMapping("/articleList")
    public Result articleList(Integer pageNum, Integer pageSize, Long categoryId) {
        return articleService.articleList(pageNum,pageSize,categoryId);
    }

    @GetMapping("/{id}")
    public Result articleDetail(@PathVariable Long id) {
        return articleService.articleDetail(id);
    }


}
