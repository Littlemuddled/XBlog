package com.kun.service.impl;

import com.kun.constants.RedisKey;
import com.kun.domain.Result;
import com.kun.domain.entity.LoginUser;
import com.kun.domain.entity.User;
import com.kun.domain.vo.UserInfoVo;
import com.kun.domain.vo.UserLoginVO;
import com.kun.enums.AppHttpCodeEnum;
import com.kun.exception.SystemException;
import com.kun.service.ILoginService;
import com.kun.utils.BeanCopyUtils;
import com.kun.utils.JwtUtil;
import com.kun.utils.RedisCache;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @author kun
 * @since 2022-11-19 17:07
 */
@Service
public class LoginServiceImpl implements ILoginService {

    @Resource
    private AuthenticationManager authenticationManager;
    @Resource
    private RedisCache redisCache;

    @Override
    public Result login(User user) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword());
        //authenticationManager最终会调用userDetailsService进行用户认证  (创建UserDetailsService实现类惊醒校验)
        //loadUserByUsername方法返回的信息
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        if (Objects.isNull(authenticate)) {
            throw new RuntimeException("用户名或密码错误！！！");
        }

        // 获取userId生成token
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        Long userId = loginUser.getUser().getId();
        String token = JwtUtil.createJWT(String.valueOf(userId));

        // 将用户信息存入redis
        redisCache.setCacheObject(RedisKey.LOGIN_KEY + userId, loginUser);

        // token和userInfo封装并返回
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(loginUser.getUser(), UserInfoVo.class);
        UserLoginVO userLoginVO = new UserLoginVO(token, userInfoVo);

        return Result.okResult(userLoginVO);
    }

    @Override
    public Result logout() {
        //获取用户信息
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        //删除redis中的用户信息
        boolean b = redisCache.deleteObject(RedisKey.LOGIN_KEY + loginUser.getUser().getId());
        if (!b) {
            throw new SystemException(AppHttpCodeEnum.SYSTEM_ERROR);
        }
        return Result.okResult();
    }
}
