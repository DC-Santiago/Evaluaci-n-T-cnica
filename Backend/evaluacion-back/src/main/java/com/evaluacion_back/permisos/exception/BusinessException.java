package com.evaluacion_back.permisos.exception;

/**
 *
 * @author Sadec
 */
public class BusinessException extends RuntimeException {

    public BusinessException(String message) {
        super(message);
    }
}