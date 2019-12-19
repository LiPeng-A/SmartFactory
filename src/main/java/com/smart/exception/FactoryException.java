package com.smart.exception;

import com.smart.enums.ExceptionEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class FactoryException extends RuntimeException {
    private ExceptionEnum exceptionEnum;
}
