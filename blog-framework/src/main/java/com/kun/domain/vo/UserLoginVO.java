package com.kun.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author kun
 * @since 2022-11-19 17:55
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginVO {

    private String token;
    private UserInfoVo userInfo;
}
