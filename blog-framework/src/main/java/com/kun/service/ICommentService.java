package com.kun.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kun.domain.Result;
import com.kun.domain.entity.Comment;

/**
 * <p>
 * 评论表 服务类
 * </p>
 *
 * @author kun
 * @since 2022-11-18
 */
public interface ICommentService extends IService<Comment> {

    /**
     * 分页查询根评论
     *
     * @param commentType 评论类型
     * @param articleId   文章id
     * @param pageNum     当前页
     * @param pageSize    每页记录数
     * @return
     */
    Result commentList(String commentType, Long articleId, Integer pageNum, Integer pageSize);

    /**
     * 添加评论
     * @param comment
     * @return
     */
    Result addComment(Comment comment);
}
