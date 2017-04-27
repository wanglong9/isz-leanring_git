package com.e7code.common.mvc.handler;

import com.e7code.common.api.constant.ErrorCodes;
import com.e7code.common.api.exception.ValidateException;
import com.e7code.common.api.bean.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by ssr on 2017/3/21.
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result<?> defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
        if(e instanceof ValidateException) {
            ValidateException ve = (ValidateException)e;
            return Result.createFailResult(ve.getCode(), ve.getMessage());
        }

        return Result.createFailResult(ErrorCodes.SYSTEM_EXCEPTION);
    }
}
