package com.kun.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author kun
 * @since 2022-11-19 14:12
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryAdminVO {

    private Long id;

    private String name;

    private String description;
}
