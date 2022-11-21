package com.kun.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kun.constants.RedisKey;
import com.kun.constants.SystemConstants;
import com.kun.domain.Result;
import com.kun.domain.entity.Article;
import com.kun.domain.entity.Category;
import com.kun.domain.vo.ArticleDetailVO;
import com.kun.domain.vo.ArticleVO;
import com.kun.domain.vo.HotArticleVO;
import com.kun.domain.vo.PageVO;
import com.kun.mapper.ArticleMapper;
import com.kun.service.IArticleService;
import com.kun.service.ICategoryService;
import com.kun.utils.BeanCopyUtils;
import com.kun.utils.RedisCache;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Desc:
 * User:Administrator
 * Date:2022-11-18 19:31
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements IArticleService {

    @Resource
    private ICategoryService categoryService;
    @Resource
    private RedisCache redisCache;

    @Override
    public Result hotArticleList() {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        //文章为正常状态,按浏览量进行排序
        queryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL).orderByDesc(Article::getViewCount);

        Page<Article> articlePage = new Page<>(SystemConstants.ARTICLE_PAGE_CUR,SystemConstants.ARTICLE_PAGE_SIZE);
        Page<Article> page = page(articlePage, queryWrapper);
        List<Article> articleList = page.getRecords();

        //转化为HotArticleVO对象返回
        List<HotArticleVO> hotArticleVOList = BeanCopyUtils.copyBeanList(articleList, HotArticleVO.class);

        return Result.okResult(hotArticleVOList);
    }

    @Override
    public Result articleList(Integer pageNum, Integer pageSize, Long categoryId) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        //如果有根据categoryId查，没有查全部
        queryWrapper.eq(Objects.nonNull(categoryId) && categoryId > 0, Article::getCategoryId, categoryId);
        queryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL)
                .orderByDesc(Article::getIsTop);

        Page<Article> articlePage = new Page<>(pageNum, pageSize);
        Page<Article> page = this.page(articlePage, queryWrapper);
        List<Article> articleList = page.getRecords();

/*
        articleList.stream()
                .forEach(article -> article.setViewCount(((Integer)redisCache.getCacheMapValue(RedisKey.ARTICLE_VIEW_COUNT, (article.getId()).toString())).longValue()));
*/
        //根据id查询categoryName
/*        for (Article article : articleList) {
            Category category = categoryService.getById(article.getCategoryId());
            String categoryName = category.getName();
            article.setCategoryName(categoryName);
        }*/
        List<Article> articles = articleList.stream()
                .map(article -> article.setCategoryName((categoryService.getById(article.getCategoryId())).getName()))
                .collect(Collectors.toList());

        //搜集数据
        List<ArticleVO> articleVOS = BeanCopyUtils.copyBeanList(articles, ArticleVO.class);
        PageVO<ArticleVO> pageVO = new PageVO<>(articleVOS, page.getTotal());

        return Result.okResult(pageVO);
    }

    @Override
    public Result articleDetail(Long id) {
        Article article = this.getById(id);
        //查询redis中的浏览量
        Integer viewCount = redisCache.getCacheMapValue(RedisKey.ARTICLE_VIEW_COUNT, id.toString());
        article.setViewCount(viewCount.longValue());
        ArticleDetailVO articleDetailVO = BeanCopyUtils.copyBean(article, ArticleDetailVO.class);


        //根据分类id查询分类名
        Long categoryId = articleDetailVO.getCategoryId();
        Category category = categoryService.getById(categoryId);
        if (category != null) {
            articleDetailVO.setCategoryName(category.getName());
        }

        return Result.okResult(articleDetailVO);
    }

    @Override
    public Result updateViewCount(Long id) {
        redisCache.incrementCacheMapValue(RedisKey.ARTICLE_VIEW_COUNT, id.toString(), 1);
        return Result.okResult();
    }
}
