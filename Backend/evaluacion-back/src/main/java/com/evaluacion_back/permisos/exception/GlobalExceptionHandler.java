package com.evaluacion_back.permisos.exception;

/**
 *
 * @author SantiagoDC
 */
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<?> handleBusiness(BusinessException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
public ResponseEntity<?> handleGeneral(Exception ex) {
    ex.printStackTrace(); // 👈 Agrega esto para ver en la terminal de NetBeans qué línea exacta falló
    return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body("Error interno del servidor");
}
}