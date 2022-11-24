package com.kun.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kun.constants.RedisKey;
import com.kun.constants.SystemConstants;
import com.kun.domain.Result;
import com.kun.domain.dto.AddArticleDto;
import com.kun.domain.dto.ArticleDto;
import com.kun.domain.entity.Article;
import com.kun.domain.entity.ArticleTag;
import com.kun.domain.entity.Category;
import com.kun.domain.entity.Tag;
import com.kun.domain.vo.*;
import com.kun.enums.AppHttpCodeEnum;
import com.kun.exception.SystemException;
import com.kun.mapper.ArticleMapper;
import com.kun.service.IArticleService;
import com.kun.service.IArticleTagService;
import com.kun.service.ICategoryService;
import com.kun.utils.BeanCopyUtils;
import com.kun.utils.RedisCache;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    private IArticleTagService articleTagService;
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

    @Override
    @Transactional
    public Result add(AddArticleDto addArticleDto) {
        Article article = BeanCopyUtils.copyBean(addArticleDto, Article.class);
        //添加博客  会自动将插入数据的id插入对象的id属性
        boolean b = this.save(article);
        if (!b) {
            throw new SystemException(AppHttpCodeEnum.ARTICLE_SAVE_FAIL);
        }
        List<Long> tagIds = addArticleDto.getTags();
        List<ArticleTag> articleTags = tagIds.stream()
                .map(tagId -> new ArticleTag(article.getId(), tagId))
                .collect(Collectors.toList());

        //添加博客和标签的关联
        articleTagService.saveBatch(articleTags);

        return Result.okResult();
    }

    @Override
    public Result getAllArticle(ArticleDto articleDto, Integer pageNum, Integer pageSize) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.hasText(articleDto.getTitle()),Article::getTitle,articleDto.getTitle());
        queryWrapper.like(StringUtils.hasText(articleDto.getSummary()),Article::getSummary,articleDto.getSummary());

        Page<Article> page = new Page<>(pageNum,pageSize);
        Page<Article> articlePage = this.page(page, queryWrapper);

        List<AdminArticleVO> adminArticleVOS = BeanCopyUtils.copyBeanList(articlePage.getRecords(), AdminArticleVO.class);

        return Result.okResult(new PageVO<AdminArticleVO>(adminArticleVOS,articlePage.getTotal()));
    }

    @Override
    public Result getByArticleId(Long id) {
        Article article = this.getById(id);
        LambdaQueryWrapper<ArticleTag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ArticleTag::getArticleId, id);

        List<ArticleTag> articleTags = articleTagService.list(queryWrapper);
        List<Long> tagIds = articleTags.stream()
                .map(ArticleTag::getTagId)
                .collect(Collectors.toList());

        AdminArticle2VO adminArticle2VO = BeanCopyUtils.copyBean(article, AdminArticle2VO.class);
        adminArticle2VO.setTags(tagIds);

        return Result.okResult(adminArticle2VO);
    }

    @Override
    @Transactional
    public Result updateArticle(AdminArticle2VO adminArticle2VO) {
        Article article = BeanCopyUtils.copyBean(adminArticle2VO, Article.class);
        this.updateById(article);

        //删除原有的博客文章和标签的关联
        LambdaQueryWrapper<ArticleTag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ArticleTag::getArticleId, article.getId());
        articleTagService.remove(queryWrapper);

        //添加新的博客和标签的关联关系
        List<ArticleTag> articleTagList = adminArticle2VO.getTags().stream()
                .map(tagId -> new ArticleTag(article.getId(), tagId))
                .collect(Collectors.toList());

        articleTagService.saveBatch(articleTagList);
        return Result.okResult();
    }

    @Override
    @Transactional
    public Result deleteArticle(Long id) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Article::getId,id);
        this.remove(queryWrapper);
        //删除文章与标签的关联
        LambdaQueryWrapper<ArticleTag> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ArticleTag::getArticleId,id);
        articleTagService.remove(wrapper);
        return Result.okResult();
    }
}
