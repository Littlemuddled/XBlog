package com.kun.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author kun
 * @since 2022-11-22 20:34
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleDto {

    private String title;
    private String summary;
}
