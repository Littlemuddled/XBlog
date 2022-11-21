package com.kun.domain.vo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author kun
 * @since 2022-11-19 17:54
 */
@Data
@Accessors(chain = true)
public class UserInfoVo {
    /**
     * 主键
     */
    private Long id;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 头像
     */
    private String avatar;

    private String sex;

    private String email;


}
