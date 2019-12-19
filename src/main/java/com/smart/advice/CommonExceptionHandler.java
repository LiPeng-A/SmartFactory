package com.smart.advice;

import com.smart.exception.FactoryException;
import com.smart.view.ExceptionResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * 通用异常处理类
 */

@ControllerAdvice
public class CommonExceptionHandler {

    @ExceptionHandler(FactoryException.class)
    public ResponseEntity<ExceptionResult> handlerException(FactoryException e){

        return ResponseEntity.status
                (e.getExceptionEnum().getStatus())
                .body(new ExceptionResult(e.getExceptionEnum()));
    }
}
