package com.cos.photogramstart.handler.aop;

import com.cos.photogramstart.handler.ex.CustomValidationApiException;
import com.cos.photogramstart.handler.ex.CustomValidationException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

@Component // RestController, Service 등 모든 것들이 Component를 상속해서 만들어져 있음.
@Aspect // //Aspect 역할을 할 클래스를 선언하기 위해 어노테이션 선언
public class ValidationAdvice {

    // execution( 접근지정자타입(예:public,private,*(모든타입) 등) package.*Controller(뒤에 Controller라고 붙은 모든것).*(메소드 파라미터:(..)은 모든 파라미터를 뜻함) )
    // : 괄호 안에 있는 메소드들에 aop를 적용함
    @Around("execution(* com.cos.photogramstart.web.api.*Controller.*(..))") // Around : 메소드 실행 전부터 끝날 때까지 관여
    // ProceedingJoinPoint
    // : 개발도중 호출되는 대상 객체에 대한 정보, 실행되는 메서드에 대한 정보, 메서드를 호출할 때 전달된 인자에 대한 정보가 필요할 때 이들 정보에 접근할 수 있는 인터페이스
    public Object apiAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        // commentSave 메소드를 실행 한다고 했을 때
        // proceedingJoinPoint => commentSave 메소드의 모든 곳에 접근할 수 있는 인터페이스
        // commentSave 메소드보다 먼저 실행

        System.out.println("web api 컨트롤러========================");
        Object[] args = proceedingJoinPoint.getArgs(); // 메소드의 매개변수를 가져옴

        // 유효성 검사
        for (Object arg : args) {
            if(arg instanceof BindingResult) { // args 배열 중에 BindigResult 타입이 있는지 확인
                System.out.println("유효성 검사를 하는 함수입니다.");
                BindingResult bindingResult = (BindingResult) arg; // args[]에 들어있던 BindingResult 타입 인스턴스를 변수로 참조

                if(bindingResult.hasErrors()) { // 에러가 있는지 확인
                    Map<String, String> errorMap = new HashMap<>();

                    for(FieldError error:bindingResult.getFieldErrors()) {
                        errorMap.put(error.getField(), error.getDefaultMessage());
                    }

                    throw new CustomValidationApiException("유효성 검사 실패함", errorMap);
                }
            }

        }
        
        return proceedingJoinPoint.proceed(); // commentSave 함수가 실행됨
    }

    @Around("execution(* com.cos.photogramstart.web.*Controller.*(..))")
    public Object advice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        System.out.println("web 컨트롤러=====================");

        Object[] args = proceedingJoinPoint.getArgs(); // 메소드의 매개변수를 가져옴
        for (Object arg : args) {
            if(arg instanceof BindingResult) {
                System.out.println("유효성 검사를 하는 함수입니다.");
                BindingResult bindingResult = (BindingResult) arg;

                if(bindingResult.hasErrors()) {
                    Map<String, String> errorMap = new HashMap<>();

                    for(FieldError error:bindingResult.getFieldErrors()) {
                        errorMap.put(error.getField(), error.getDefaultMessage());
                    }
                    throw new CustomValidationException("유효성 검사 실패함", errorMap);
                }
            }

        }

        return  proceedingJoinPoint.proceed();
    }

}
