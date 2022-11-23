package com.kun.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kun.domain.Result;
import com.kun.domain.entity.User;
import com.kun.domain.entity.UserRole;
import com.kun.domain.vo.PageVO;
import com.kun.domain.vo.UserInfoVo;
import com.kun.domain.vo.UserVo;
import com.kun.enums.AppHttpCodeEnum;
import com.kun.exception.SystemException;
import com.kun.mapper.UserMapper;
import com.kun.service.IUserRoleService;
import com.kun.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kun.utils.BeanCopyUtils;
import com.kun.utils.SecurityUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author kun
 * @since 2022-11-19
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Resource
    private PasswordEncoder passwordEncoder;
    @Resource
    private IUserRoleService userRoleService;

    @Override
    public Result userInfo() {
        Long userId = SecurityUtils.getUserId();
        User user = this.getById(userId);
        if (StringUtils.isEmpty(user)) {
            throw new SystemException(AppHttpCodeEnum.USER_NO_FIND);
        }
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(user, UserInfoVo.class);
        return Result.okResult(userInfoVo);
    }

    @Override
    public Result updateUserInfo(User user) {
        boolean b = this.updateById(user);
        if (!b) {
            throw new SystemException(AppHttpCodeEnum.UPDATE_FAIL);
        }
        return Result.okResult();
    }

    @Override
    public Result register(User user) {
        //数据非空判断
        if (!StringUtils.hasText(user.getUserName())) {
            throw new SystemException(AppHttpCodeEnum.USERNAME_NOT_NULL);
        }
        if (!StringUtils.hasText(user.getPassword())) {
            throw new SystemException(AppHttpCodeEnum.PASSWORD_NOT_NULL);
        }
        if (!StringUtils.hasText(user.getNickName())) {
            throw new SystemException(AppHttpCodeEnum.NICKNAME_NOT_NULL);
        }
        if (!StringUtils.hasText(user.getEmail())) {
            throw new SystemException(AppHttpCodeEnum.EMAIL_NOT_NULL);
        }

        //判断username是否已经存在
        if (this.usernameExist(user.getUserName())) {
            throw new SystemException(AppHttpCodeEnum.USERNAME_EXIST);
        }
        if (this.nicknameExist(user.getNickName())) {
            throw new SystemException(AppHttpCodeEnum.NICKNAME_EXIST);
        }

        //对密码加密
        String encode = passwordEncoder.encode(user.getPassword());
        user.setPassword(encode);
        boolean b = this.save(user);
        if (!b) {
            throw new SystemException(AppHttpCodeEnum.REGISTER_FAIL);
        }
        return Result.okResult();
    }

    @Override
    public Result selectUserPage(User user, Integer pageNum, Integer pageSize) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.hasText(user.getUserName()), User::getUserName, user.getUserName());
        queryWrapper.eq(StringUtils.hasText(user.getStatus()), User::getStatus, user.getStatus());
        queryWrapper.eq(StringUtils.hasText(user.getPhonenumber()), User::getPhonenumber, user.getPhonenumber());

        Page<User> page = new Page<>(pageNum, pageSize);
        this.page(page, queryWrapper);

        //转换成VO
        List<User> users = page.getRecords();
        List<UserVo> userVoList = users.stream()
                .map(u -> BeanCopyUtils.copyBean(u, UserVo.class))
                .collect(Collectors.toList());
        PageVO<UserVo> pageVo = new PageVO<>(userVoList, page.getTotal());
        return Result.okResult(pageVo);
    }

    @Override
    public boolean checkUserNameUnique(String userName) {
        return count(Wrappers.<User>lambdaQuery().eq(User::getUserName, userName)) == 0;
    }

    @Override
    public boolean checkPhoneUnique(User user) {
        return count(Wrappers.<User>lambdaQuery().eq(User::getPhonenumber, user.getPhonenumber())) == 0;
    }

    @Override
    public boolean checkEmailUnique(User user) {
        return count(Wrappers.<User>lambdaQuery().eq(User::getEmail, user.getEmail())) == 0;
    }

    @Override
    public Result addUser(User user) {
        //密码加密处理
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        save(user);
        if (user.getRoleIds() != null && user.getRoleIds().length > 0) {
            insertUserRole(user);
        }
        return Result.okResult();
    }

    @Override
    @Transactional
    public void updateUser(User user) {
        // 删除用户与角色关联
        LambdaQueryWrapper<UserRole> userRoleUpdateWrapper = new LambdaQueryWrapper<>();
        userRoleUpdateWrapper.eq(UserRole::getUserId,user.getId());
        userRoleService.remove(userRoleUpdateWrapper);
        // 新增用户与角色管理
        insertUserRole(user);
        // 更新用户信息
        updateById(user);
    }

    private void insertUserRole(User user) {
        List<UserRole> sysUserRoles = Arrays.stream(user.getRoleIds())
                .map(roleId -> new UserRole(user.getId(), roleId)).collect(Collectors.toList());
        userRoleService.saveBatch(sysUserRoles);
    }

    private boolean usernameExist(String userName) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName, userName);
        return this.count(queryWrapper) > 0;
    }

    private boolean nicknameExist(String nickname) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getNickName, nickname);
        return this.count(queryWrapper) > 0;
    }
}
