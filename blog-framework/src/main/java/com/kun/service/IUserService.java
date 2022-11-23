package com.kun.service;

import com.kun.domain.Result;
import com.kun.domain.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author kun
 * @since 2022-11-19
 */
public interface IUserService extends IService<User> {

    /**
     * 个人信息查询
     * @return
     */
    Result userInfo();

    /**
     * 更新用户信息
     * @param user
     * @return
     */
    Result updateUserInfo(User user);

    /**
     * 用户注册
     * @param user
     * @return
     */
    Result register(User user);

    Result selectUserPage(User user, Integer pageNum, Integer pageSize);

    boolean checkUserNameUnique(String userName);

    boolean checkPhoneUnique(User user);

    boolean checkEmailUnique(User user);

    Result addUser(User user);

    void updateUser(User user);
}
