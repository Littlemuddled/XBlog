package com.kun.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kun.constants.SystemConstants;
import com.kun.domain.Result;
import com.kun.domain.entity.Comment;
import com.kun.domain.vo.CommentVO;
import com.kun.domain.vo.PageVO;
import com.kun.enums.AppHttpCodeEnum;
import com.kun.exception.SystemException;
import com.kun.mapper.CommentMapper;
import com.kun.service.ICommentService;
import com.kun.service.IUserService;
import com.kun.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 评论表 服务实现类
 * </p>
 *
 * @author kun
 * @since 2022-11-18
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements ICommentService {

    @Resource
    private IUserService userService;

    @Override
    public Result commentList(String commentType, Long articleId, Integer pageNum, Integer pageSize) {
        //查询对应文章的根评论（rootId=-1）
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SystemConstants.ARTICLE_COMMENT.equals(commentType),Comment::getArticleId,articleId);
        queryWrapper.eq(Comment::getRootId, SystemConstants.ROOT_ID);
        queryWrapper.eq(Comment::getType, commentType);

        Page<Comment> page = new Page<>(pageNum,pageSize);
        Page<Comment> commentPage = this.page(page, queryWrapper);
        List<Comment> commentList = commentPage.getRecords();
        long total = commentPage.getTotal();

        //将数据封装成CommentVO返回给前端
        List<CommentVO> commentVOList = this.toCommentVOList(commentList);

        //查询所有根评论对应的子评论
        commentVOList.stream().forEach(commentVO -> commentVO.setChildren(this.getChildren(commentVO.getId())));

        PageVO<CommentVO> pageVO = new PageVO<>(commentVOList,total);
        return Result.okResult(pageVO);
    }

    @Override
    public Result addComment(Comment comment) {
        if (!StringUtils.hasText(comment.getContent())) {
            throw new SystemException(AppHttpCodeEnum.CONTENT_NOT_NULL);
        }
        this.save(comment);
        return Result.okResult();
    }

    /**
     * 查询所有跟评论下的子评论
     * @param id 根评论id
     * @return 子评论集合
     */
    private List<CommentVO> getChildren(Long id) {
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getRootId,id).orderByAsc(Comment::getUpdateTime);
        List<Comment> commentList = this.list(queryWrapper);
        List<CommentVO> commentVOList = this.toCommentVOList(commentList);
        return commentVOList;
    }

    /**
     * 在返回的对象信息中加连个字段信息 (toCommentUserName和username)
     * @param commentList
     * @return
     */
    private List<CommentVO> toCommentVOList(List<Comment> commentList) {
        List<CommentVO> commentVOList = BeanCopyUtils.copyBeanList(commentList, CommentVO.class);
        for (CommentVO commentVO : commentVOList) {
            String nickName = userService.getById(commentVO.getCreateBy()).getNickName();
            commentVO.setUsername(nickName);
            if (commentVO.getToCommentUserId() != -1) {
                String toCommentNickName = userService.getById(commentVO.getToCommentUserId()).getNickName();
                commentVO.setToCommentUserName(toCommentNickName);
            }
        }
        return commentVOList;
    }
}
