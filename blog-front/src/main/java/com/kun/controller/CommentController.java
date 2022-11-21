package com.kun.controller;

import com.kun.constants.SystemConstants;
import com.kun.domain.Result;
import com.kun.domain.entity.Comment;
import com.kun.service.ICommentService;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

/**
 * <p>
 * 评论表 前端控制器
 * </p>
 *
 * @author kun
 * @since 2022-11-18
 */
@RestController
@RequestMapping("/comment")
public class CommentController {

    @Resource
    private ICommentService commentService;

    @GetMapping("/commentList")
    public Result commentList(Long articleId,Integer pageNum,Integer pageSize) {
        return commentService.commentList(SystemConstants.ARTICLE_COMMENT,articleId,pageNum,pageSize);
    }

    @PostMapping
    public Result addComment(@RequestBody Comment comment) {
        return commentService.addComment(comment);
    }

    @GetMapping("/linkCommentList")
    public Result linkCommentList(Integer pageNum,Integer pageSize) {
        return commentService.commentList(SystemConstants.LINK_COMMENT,null,pageNum, pageSize);
    }

}
