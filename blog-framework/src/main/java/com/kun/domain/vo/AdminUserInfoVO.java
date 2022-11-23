package com.kun.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author kun
 * @since 2022-11-22 14:05
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class AdminUserInfoVO {

    private List<String> permissions;

    private List<String> roles;

    private UserInfoVo user;
}
