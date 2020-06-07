package com.store.bookshopadmin.web.support;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus
    public Map<String, Object> handleException(RuntimeException exception) {
        Map<String, Object> result = new HashMap<>();
        result.put("result", "fail");
        result.put("errMsg", "test");
        return result;
    }
}
