package com.cos.photogramstart.handler.ex;

import java.util.Map;

public class CustomException extends RuntimeException{

    // 객체를 구분할 때 쓰는 ID(JVM에게 중요한 거지 우리에게 중요한 게 아님)
    private static final long serialVersionUID = 1L;


    public CustomException(String message) {
        // RuntimeException의 부모 함수에 getMessage 메소드가 구현되어 있어 따로 getMessage를 구현 할 필요 없이 super를 이용해 부모에게 값을 넘기면 됨
        super(message);
    }

}
