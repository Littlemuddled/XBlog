package com.kun.controller;

import com.kun.domain.Result;
import com.kun.domain.entity.User;
import com.kun.service.IUserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author kun
 * @since 2022-11-19
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private IUserService userService;

    @GetMapping("/userInfo")
    public Result userInfo() {
        return userService.userInfo();
    }

    @PutMapping("/userInfo")
    public Result updateUserInfo(@RequestBody User user) {
        return userService.updateUserInfo(user);
    }

    @PostMapping("/register")
    public Result register(@RequestBody User user) {
        return userService.register(user);
    }
}
