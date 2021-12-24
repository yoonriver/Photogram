package com.cos.photogramstart.handler;

import com.cos.photogramstart.handler.ex.CustomApiException;
import com.cos.photogramstart.handler.ex.CustomValidationApiException;
import com.cos.photogramstart.handler.ex.CustomValidationException;
import com.cos.photogramstart.util.Script;
import com.cos.photogramstart.web.dto.CMREspDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@ControllerAdvice // 모든 Exception을 낚아채는 어노테이션
public class ControllerExceptionHandler {
    //CMRespDto, Script 비교
    // 1. 클라이언트에게 응답할 때는 Script가 좋음
    // 2. Ajax 통신 - CMRespDto가 좋음
    // 3. Android 통신 - CMRespDto가 좋음

    // 예외를 처리하는 어노테이션
    @ExceptionHandler(CustomValidationException.class)
    public String validationException(CustomValidationException e) {
        if(e.getErrorMap() == null) {
            return Script.back(e.getMessage());
        }else {
            return Script.back(e.getErrorMap().toString());
        }
    }

    @ExceptionHandler(CustomValidationApiException.class)
    public ResponseEntity<?> validationApiException(CustomValidationApiException e) {

        return new ResponseEntity<CMREspDto<?>>(
                new CMREspDto<>(-1,e.getMessage(),e.getErrorMap()),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CustomApiException.class)
    public ResponseEntity<?> apiException(CustomApiException e) {

        return new ResponseEntity<CMREspDto<?>>(
                new CMREspDto<>(-1,e.getMessage(),null),
                HttpStatus.BAD_REQUEST);
    }
}
