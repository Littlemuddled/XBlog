package com.kun.service;

import com.kun.domain.Result;
import com.kun.domain.entity.User;

/**
 * @author kun
 * @since 2022-11-21 20:36
 */
public interface ISystemLoginService {

    /**
     * 后台登录
     * @param user
     * @return
     */
    Result login(User user);

    Result logout();

}
