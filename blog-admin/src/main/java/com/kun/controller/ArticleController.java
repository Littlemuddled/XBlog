package com.kun.controller;

import com.kun.domain.Result;
import com.kun.domain.dto.AddArticleDto;
import com.kun.domain.dto.ArticleDto;
import com.kun.domain.entity.Article;
import com.kun.domain.vo.AdminArticle2VO;
import com.kun.service.IArticleService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author kun
 * @since 2022-11-22 22:22
 */
@RestController
@RequestMapping("/content/article")
public class ArticleController {

    @Resource
    private IArticleService articleService;

    @PostMapping
    public Result add(@RequestBody AddArticleDto addArticleDto){
        return articleService.add(addArticleDto);
    }

    @GetMapping("/list")
    public Result getAllArticle(ArticleDto articleDto, Integer pageNum, Integer pageSize) {
        return articleService.getAllArticle(articleDto,pageNum,pageSize);
    }

    @GetMapping("/{id}")
    public Result getById(@PathVariable Long id) {
        return articleService.getByArticleId(id);
    }

    @PutMapping
    public Result updateArticle(@RequestBody AdminArticle2VO adminArticle2VO) {
        return articleService.updateArticle(adminArticle2VO);
    }

    @DeleteMapping("/{id}")
    public Result deleteArticle(@PathVariable Long id) {
        return articleService.deleteArticle(id);
    }

}