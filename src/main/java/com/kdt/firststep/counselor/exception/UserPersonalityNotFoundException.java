package com.kdt.firststep.counselor.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// 사용자 정의 예외
@ResponseStatus(HttpStatus.BAD_REQUEST) // 400번 에러로 처리
public class UserPersonalityNotFoundException extends RuntimeException {
    public UserPersonalityNotFoundException(String message) {
        super(message);
    }
}
