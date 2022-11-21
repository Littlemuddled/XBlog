package com.kun.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author kun
 * @since 2022-11-19 14:32
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageVO<T> {

    private List<T> rows;
    private Long total;
 }
