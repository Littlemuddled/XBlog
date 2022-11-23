package com.kun.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author kun
 * @since 2022-11-23 21:06
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class MenuTreeVo {

    private static final long serialVersionUID = 1L;

    /** 节点ID */
    private Long id;

    /** 节点名称 */
    private String label;

    private Long parentId;

    /** 子节点 */
//    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<MenuTreeVo> children;
}