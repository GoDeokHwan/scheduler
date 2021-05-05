package com.example.scheduler.api.config;

import com.example.scheduler.api.support.api.ApiException;
import com.example.scheduler.api.support.api.ApiResult;
import com.example.scheduler.api.support.api.ApiStatus;
import com.example.scheduler.api.support.api.ApiStatusResponsible;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;


@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler
    @ResponseBody
    public ApiResult handleException(HttpServletRequest req, Exception e) {
        log.info("requestURL :: {}", req.getRequestURL());
        if (e instanceof ApiException) {
            ApiStatusResponsible responsible = ((ApiException) e).getApiStatus();
            if (responsible.isShort()) {
                log.info("{}", e.getMessage());
            } else {
                log.error(e.getMessage(), e);
            }
            return ApiResult.of(((ApiException) e).getApiStatus());
        } else if (e instanceof HttpMediaTypeNotAcceptableException) {
            log.info("웹소캣에 의한 예외 발생");
            return ApiResult.of(ApiStatus.SUCCESS);
        } else if (e instanceof HttpRequestMethodNotSupportedException) {
            return ApiResult.of(ApiStatus.REQUEST_METHOD_NOT_SUPPORTED);
        } else if (e instanceof HttpMessageNotReadableException) {
            log.info("HttpMessageNotReadableException 예외 발생.");
            return ApiResult.of(ApiStatus.BAD_REQUEST_ERROR);
        } else if (e instanceof MissingServletRequestParameterException) {
            log.info("필수 파라미터가 없어서 예외 발생.");
            return ApiResult.of(ApiStatus.MISSING_REQUEST_PARAMETER);
        }
        log.error(e.getMessage(), e);
        return ApiResult.of(ApiStatus.ERROR);
    }
}
