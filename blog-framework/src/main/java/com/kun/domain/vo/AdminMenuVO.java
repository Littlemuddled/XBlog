package com.kun.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 菜单权限表
 * </p>
 *
 * @author kun
 * @since 2022-11-19
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class AdminMenuVO implements Serializable {

    private Long id;
    private String menuName;
    private Long parentId;
    private Integer orderNum;
    private String path;
    private String perms;
    private String component;
    private Integer isFrame;
    private String menuType;
    private String visible;
    private String status;
    private String icon;
    private String remark;
    private LocalDateTime createTime;
}
