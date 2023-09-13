package com.lingxi.exception;


import com.lingxi.result.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 全局异常
 */
@ControllerAdvice(basePackages = "com.lingxi.controller")  //控制器增强类
@Slf4j
@ResponseBody
public class ExceptionCatch {

//    @ExceptionHandler(Exception.class)
//    public ResponseResult exception(Exception e) throws Exception {
//        if (e instanceof AccessDeniedException) {
//            log.error("AccessDeniedException:{}", true);
//            throw e; // 如果是 AccessDeniedException，则重新抛出异常，让 Spring Security 处理
//        }
//        e.printStackTrace();
//        log.error("catch exception:{}", e.getMessage());
//        return ResponseResult.errorResult(AppHttpCodeEnum.SERVER_ERROR);
//    }


    /**
     * 处理可控异常  自定义异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(CustomException.class)
    public ResponseResult exception(CustomException e) {
        log.error("catch exception:{}", e);
        return ResponseResult.errorResult(e.getAppHttpCodeEnum());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseResult handleValidationException(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        List<ObjectError> allErrors = bindingResult.getAllErrors();
        List<String> errorMessages = allErrors.stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
        return ResponseResult.errorResult(400, errorMessages);
    }
}
