package com.kun.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author kun
 * @since 2022-11-18
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class CommentVO {

    private Long id;

    /**
     * 文章id
     */
    private Long articleId;

    /**
     * 根评论id
     */
    private Long rootId;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 所回复的目标评论的userid
     */
    private Long toCommentUserId;

    /**
     * 所回复的目标评论的名字
     */
    private String toCommentUserName;

    /**
     * 用户昵称
     */
    private String username;

    /**
     * 回复目标评论id
     */
    private Long toCommentId;

    private Long createBy;

    private LocalDateTime createTime;

    /**
     * 根评论下面的子评论
     */
    private List<CommentVO> children;

}
