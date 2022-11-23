package com.kun.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kun.domain.Result;
import com.kun.domain.dto.AddArticleDto;
import com.kun.domain.dto.ArticleDto;
import com.kun.domain.entity.Article;
import com.kun.domain.vo.AdminArticle2VO;

/**
 * Desc:
 * User:Administrator
 * Date:2022-11-18 19:30
 */
public interface IArticleService extends IService<Article> {

    /**
     * 查询热门文章列表
     * @return
     */
    Result hotArticleList();

    /**
     * 分页查询文章列表
     * @param pageNum 当前页数
     * @param pageSize 每页记录数
     * @param categoryId 文章所属分类id（为空查询全部，不为空根据分类查询）
     * @return
     */
    Result articleList(Integer pageNum, Integer pageSize, Long categoryId);

    /**
     * 根据id查询文章详情信息
     * @param id 文章id
     * @return
     */
    Result articleDetail(Long id);

    /**
     * 更新博客浏览量
     * @param id 博客id
     * @return
     */
    Result updateViewCount(Long id);

    /**
     * 添加博客文章
     * @param addArticleDto
     * @return
     */
    Result add(AddArticleDto addArticleDto);

    /**
     * 模糊分页查询博客文章
     * @param articleDto 接收的模糊查询字段对象
     * @param pageNum 当前页数
     * @param pageSize 每页记录数
     * @return
     */
    Result getAllArticle(ArticleDto articleDto, Integer pageNum, Integer pageSize);

    /**
     * 根据id查询文章
     * @param id 文章id
     * @return
     */
    Result getByArticleId(Long id);

    /**
     * 更新博客文章
     * @param adminArticle2VO
     * @return
     */
    Result updateArticle(AdminArticle2VO adminArticle2VO);

    /**
     * 根据id删除文章
     * @param id
     * @return
     */
    Result deleteArticle(Long id);
}
