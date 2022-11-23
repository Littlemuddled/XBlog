package com.kun.domain.vo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 文章表
 * </p>
 *
 * @author kun
 * @since 2022-11-18
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)   //set方法返回对象本身
public class AdminArticle2VO implements Serializable {

    private Long id;
    private String title;
    private String content;
    private String summary;
    private Long categoryId;
    private String thumbnail;
    private String isTop;
    private String status;
    private Long viewCount;
    private String isComment;
    private Long createBy;
    private LocalDateTime createTime;
    private Long updateBy;
    private LocalDateTime updateTime;
    private Integer delFlag;
    private List<Long> tags;

}
