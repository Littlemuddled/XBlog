package com.kun.filter;

import com.alibaba.fastjson.JSON;
import com.kun.constants.RedisKey;
import com.kun.constants.SystemConstants;
import com.kun.domain.Result;
import com.kun.domain.entity.LoginUser;
import com.kun.domain.vo.UserLoginVO;
import com.kun.enums.AppHttpCodeEnum;
import com.kun.utils.JwtUtil;
import com.kun.utils.RedisCache;
import com.kun.utils.WebUtils;
import io.jsonwebtoken.Claims;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * @author kun
 * @since 2022-11-19 20:09
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Resource
    private RedisCache redisCache;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader(SystemConstants.HEADER_TOKEN);
        if (!StringUtils.hasText(token)) {
            //说明接口不需要登录直接放行
            filterChain.doFilter(request,response);
            return;
        }
        //解析token获取userId
        String userId = null;
        try {
            Claims claims = JwtUtil.parseJWT(token);
            userId = claims.getSubject();
        } catch (Exception e) {
            e.printStackTrace();
            //响应前端需要重新登录
            Result result = Result.errorResult(AppHttpCodeEnum.NEED_LOGIN);
            WebUtils.renderString(response, JSON.toJSONString(result));
            return;
        }
        //根据userId从redis中获取用户信息
        LoginUser loginUser = redisCache.getCacheObject(RedisKey.LOGIN_KEY + userId);
        if (Objects.isNull(loginUser)) {
            //响应前端需要重新登录
            Result result = Result.errorResult(AppHttpCodeEnum.NEED_LOGIN);
            WebUtils.renderString(response, JSON.toJSONString(result));
            return;
        }
        //存入 SecurityContextHolder
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser,null,null);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        filterChain.doFilter(request,response);
    }
}
