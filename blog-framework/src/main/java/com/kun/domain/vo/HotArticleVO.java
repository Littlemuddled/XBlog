package com.kun.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Desc:
 * User:Administrator
 * Date:2022-11-18 21:53
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotArticleVO {

    private Long id;

    /**
     * 标题
     */
    private String title;

    /**
     * 访问量
     */
    private Long viewCount;
}
