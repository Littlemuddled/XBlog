package com.kun.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.kun.constants.SystemConstants;
import com.kun.domain.entity.LoginUser;
import com.kun.domain.entity.User;
import com.kun.mapper.MenuMapper;
import com.kun.mapper.UserMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * @author kun
 * @since 2022-11-19 17:17
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Resource
    private UserMapper userMapper;
    @Resource
    private MenuMapper menuMapper;

    /**
     * 用户校验
     * @param username 用户名
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName, username);
        User user = userMapper.selectOne(queryWrapper);
        if (Objects.isNull(user)) {
            throw new RuntimeException("用户不存在！！！");
        }

        // 查询权限信息封装
        // 如果是后台用户才需要查询权限封装

        if (user.getType().equals(SystemConstants.ADMIN_TYPE)) {
            List<String> perms = menuMapper.selectPermsByUserId(user.getId());
            return new LoginUser(user,perms);

        }
        return new LoginUser(user,null);
    }
}
