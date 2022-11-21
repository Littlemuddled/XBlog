package com.kun.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kun.domain.Result;
import com.kun.domain.entity.Article;

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
}
