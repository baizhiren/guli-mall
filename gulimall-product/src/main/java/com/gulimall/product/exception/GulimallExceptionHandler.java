package com.gulimall.product.exception;


import com.common.exception.CodeEnum;
import com.common.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestControllerAdvice(basePackages = "com.gulimall.product.controller")
public class GulimallExceptionHandler {

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    R handlerException(MethodArgumentNotValidException e){
        BindingResult result = e.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();
        Map<String, String> map = new HashMap<>();
        fieldErrors.forEach(item -> {
            map.put(item.getField(), item.getDefaultMessage());
        });
        return R.error(CodeEnum.VALID_EXCEPTION).put("data", map);
    }

    @ExceptionHandler(value = Exception.class)
    R handlerException(Exception e){
       log.debug("有错误", e.toString());
       e.printStackTrace();
        return R.error(CodeEnum.UNKNOWN_EXCEPTION);
    }



}
