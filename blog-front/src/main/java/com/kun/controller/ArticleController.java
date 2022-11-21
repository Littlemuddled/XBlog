package com.kun.controller;

import com.kun.domain.Result;
import com.kun.service.IArticleService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * Desc:
 * User:Administrator
 * Date:2022-11-18 19:32
 */
@RestController
@RequestMapping("/article")
@Api(tags = "博客文章")
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

    @PutMapping("/updateViewCount/{id}")
    public Result updateViewCount(@PathVariable Long id) {
        return articleService.updateViewCount(id);
    }


}
