package com.kdt.firststep.common;

import com.kdt.firststep.counselor.exception.UserPersonalityNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice // 모든 컨트롤러에서 발생하는 예외를 처리
public class GlobalExceptionHandler {

    @ExceptionHandler(UserPersonalityNotFoundException.class)
    public ResponseEntity<String> handleUserPersonalityNotFoundException(UserPersonalityNotFoundException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage()); // 400 에러와 메시지 반환
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleEntityNotFoundException(EntityNotFoundException ex) {
        return ResponseEntity.status(404).body(ex.getMessage()); // 404 에러와 메시지 반환
    }
}
