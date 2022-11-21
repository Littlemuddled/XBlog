package com.kun.service;

import com.kun.domain.Result;
import com.kun.domain.entity.User;

/**
 * @author kun
 * @since 2022-11-19 17:06
 */
public interface ILoginService {

    /**
     * 用户登录
     * @param user 用户信息对象
     * @return
     */
    Result login(User user);

    /**
     * 退出登录
     * @return
     */
    Result logout();

}
