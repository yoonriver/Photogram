package com.cos.photogramstart.handler;

import com.cos.photogramstart.handler.ex.CustomValidationException;
import com.cos.photogramstart.util.Script;
import com.cos.photogramstart.web.dto.CMREspDto;
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

        return Script.back(e.getErrorMap().toString());
    }
}
