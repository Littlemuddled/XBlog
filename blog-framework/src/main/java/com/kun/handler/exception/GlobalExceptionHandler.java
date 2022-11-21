package com.kun.handler.exception;

import com.kun.domain.Result;
import com.kun.enums.AppHttpCodeEnum;
import com.kun.exception.SystemException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 统一异常处理反映给前端
 * @author kun
 * @since 2022-11-19 21:39
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(SystemException.class)
    public Result systemExceptionHandler(SystemException e) {
        log.error("出现了异常! {}", e);
        return Result.errorResult(e.getCode(), e.getMsg());
    }

    @ExceptionHandler(Exception.class)
    public Result ExceptionHandler(Exception e) {
        log.error("出现了异常! {}", e);
        return Result.errorResult(AppHttpCodeEnum.SYSTEM_ERROR, e.getMessage());
    }
}
