package com.kun.controller;

import com.kun.domain.Result;
import com.kun.domain.entity.User;
import com.kun.enums.AppHttpCodeEnum;
import com.kun.exception.SystemException;
import com.kun.service.ILoginService;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author kun
 * @since 2022-11-19 17:03
 */
@RestController
public class LoginController {

    @Resource
    private ILoginService loginService;

    @PostMapping("/login")
    public Result login(@RequestBody User user) {
        if(!StringUtils.hasText(user.getUserName())) {
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        return loginService.login(user);
    }

    @PostMapping("/logout")
    public Result logout() {
        return loginService.logout();
    }
}
