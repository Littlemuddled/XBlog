package com.kun.controller;

import com.kun.domain.Result;
import com.kun.domain.entity.LoginUser;
import com.kun.domain.entity.Menu;
import com.kun.domain.entity.User;
import com.kun.domain.vo.AdminUserInfoVO;
import com.kun.domain.vo.MenuVO;
import com.kun.domain.vo.RoutersVO;
import com.kun.domain.vo.UserInfoVo;
import com.kun.enums.AppHttpCodeEnum;
import com.kun.exception.SystemException;
import com.kun.service.IMenuService;
import com.kun.service.IRoleService;
import com.kun.service.ISystemLoginService;
import com.kun.utils.BeanCopyUtils;
import com.kun.utils.SecurityUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author kun
 * @since 2022-11-21 20:27
 */
@RestController
public class BLoginController {

    @Resource
    private IMenuService menuService;
    @Resource
    private IRoleService roleService;
    @Resource
    private ISystemLoginService systemLoginService;

    @PostMapping("/user/login")
    public Result login(@RequestBody User user) {
        if(!StringUtils.hasText(user.getUserName())) {
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        return systemLoginService.login(user);
    }

    @PostMapping("/user/logout")
    public Result logout() {
        return systemLoginService.logout();
    }

    @GetMapping("/getInfo")
    public Result<AdminUserInfoVO> getInfo() {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        List<String> perms = menuService.selectPermsByUserId(loginUser.getUser().getId());
        List<String> roleKeys = roleService.selectRoleKeyByUserId(loginUser.getUser().getId());

        User user = loginUser.getUser();
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(user, UserInfoVo.class);
        AdminUserInfoVO adminUserInfoVO = new AdminUserInfoVO(perms, roleKeys, userInfoVo);
        return Result.okResult(adminUserInfoVO);
    }

    @GetMapping("/getRouters")
    public Result<RoutersVO> getRouters() {
        Long userId = SecurityUtils.getUserId();
        //查询menu 结果是tree的形式
        List<MenuVO> menus = menuService.selectRouterMenuTreeByUserId(userId);
        return Result.okResult(new RoutersVO(menus));
    }
}
