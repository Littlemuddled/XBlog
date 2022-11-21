package com.kun.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author kun
 * @since 2022-11-19 14:29
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleDetailVO {

    private Long id;
    private Long categoryId;

    //标题
    private String title;
    //文章摘要
    private String summary;
    //所属分类名
    private String categoryName;
    //缩略图
    private String thumbnail;

    //文章内容
    private String content;

    //访问量
    private Long viewCount;

    private LocalDateTime createTime;

}
