package com.kun.handler.security;

import com.alibaba.fastjson.JSON;
import com.kun.domain.Result;
import com.kun.enums.AppHttpCodeEnum;
import com.kun.utils.WebUtils;
import org.aspectj.weaver.ast.Instanceof;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 认证异常处理
 * @author kun
 * @since 2022-11-19 21:15
 */
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        e.printStackTrace();
        Result result = null;
        if(e instanceof BadCredentialsException){
            result = Result.errorResult(AppHttpCodeEnum.LOGIN_ERROR.getCode(),e.getMessage());
        }else if(e instanceof InsufficientAuthenticationException){
            result = Result.errorResult(AppHttpCodeEnum.NEED_LOGIN);
        }else{
            result = Result.errorResult(AppHttpCodeEnum.SYSTEM_ERROR.getCode(),"认证或授权失败!!!");
        }
        result = Result.errorResult(AppHttpCodeEnum.LOGIN_ERROR);
        WebUtils.renderString(httpServletResponse, JSON.toJSONString(result));
    }
}
