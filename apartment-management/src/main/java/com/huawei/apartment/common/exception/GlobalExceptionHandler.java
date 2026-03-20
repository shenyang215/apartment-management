package com.huawei.apartment.common.exception;

import com.huawei.apartment.common.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

/**
 * 全局异常处理器
 *
 * @author Huawei
 * @since 2026-03-18
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 业务异常处理
     */
    @ExceptionHandler(BusinessException.class)
    public Result<Void> handleBusinessException(BusinessException e) {
        log.error("业务异常: {}", e.getMessage(), e);
        return Result.fail(e.getCode(), e.getMessage());
    }

    /**
     * 参数校验异常处理
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<Void> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("参数校验异常: {}", e.getMessage(), e);
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        String message = fieldErrors.isEmpty() ? "参数校验失败" : fieldErrors.get(0).getDefaultMessage();
        return Result.fail(400, message);
    }

    /**
     * 参数绑定异常处理
     */
    @ExceptionHandler(BindException.class)
    public Result<Void> handleBindException(BindException e) {
        log.error("参数绑定异常: {}", e.getMessage(), e);
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        String message = fieldErrors.isEmpty() ? "参数绑定失败" : fieldErrors.get(0).getDefaultMessage();
        return Result.fail(400, message);
    }

    /**
     * 其他异常处理
     */
    @ExceptionHandler(Exception.class)
    public Result<Void> handleException(Exception e) {
        log.error("系统异常: {}", e.getMessage(), e);
        return Result.fail("系统异常,请联系管理员");
    }
}
