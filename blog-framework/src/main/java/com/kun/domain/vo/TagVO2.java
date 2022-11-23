package com.kun.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>
 * 标签
 * </p>
 *
 * @author kun
 * @since 2022-11-18
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TagVO2 implements Serializable {

    private Long id;

    private String name;

}
