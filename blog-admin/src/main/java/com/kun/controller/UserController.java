package com.kun.controller;

import com.kun.domain.Result;
import com.kun.domain.entity.Role;
import com.kun.domain.entity.User;
import com.kun.domain.vo.UserInfoAndRoleIdsVo;
import com.kun.enums.AppHttpCodeEnum;
import com.kun.exception.SystemException;
import com.kun.service.IRoleService;
import com.kun.service.IUserService;
import com.kun.utils.SecurityUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author kun
 * @since 2022-11-23 21:52
 */
@RestController
@RequestMapping("/system/user")
public class UserController {

    @Resource
    private IUserService userService;
    @Resource
    private IRoleService roleService;

    @GetMapping("/list")
    public Result list(User user, Integer pageNum, Integer pageSize) {
        return userService.selectUserPage(user, pageNum, pageSize);
    }

    @PostMapping
    public Result add(@RequestBody User user) {
        if (!StringUtils.hasText(user.getUserName())) {
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        if (!userService.checkUserNameUnique(user.getUserName())) {
            throw new SystemException(AppHttpCodeEnum.USERNAME_EXIST);
        }
        if (!userService.checkPhoneUnique(user)) {
            throw new SystemException(AppHttpCodeEnum.PHONE_NUMBER_EXIST);
        }
        if (!userService.checkEmailUnique(user)) {
            throw new SystemException(AppHttpCodeEnum.EMAIL_EXIST);
        }
        return userService.addUser(user);
    }

    @GetMapping(value = {"/{userId}"})
    public Result getUserInfoAndRoleIds(@PathVariable(value = "userId") Long userId) {
        List<Role> roles = roleService.selectRoleAll();
        User user = userService.getById(userId);
        //当前用户所具有的角色id列表
        List<Long> roleIds = roleService.selectRoleIdByUserId(userId);
        UserInfoAndRoleIdsVo vo = new UserInfoAndRoleIdsVo(user, roles, roleIds);
        return Result.okResult(vo);
    }

    @PutMapping
    public Result edit(@RequestBody User user) {
        userService.updateUser(user);
        return Result.okResult();
    }

    @DeleteMapping("/{userIds}")
    public Result remove(@PathVariable List<Long> userIds) {
        if(userIds.contains(SecurityUtils.getUserId())){
            return Result.errorResult(500,"不能删除当前你正在使用的用户");
        }
        userService.removeByIds(userIds);
        return Result.okResult();
    }
}
