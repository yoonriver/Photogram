package com.cos.photogramstart.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
// 어떤 데이터라도 출력할 수 있도록 제네릭으로 설정
public class CMRespDto<T> {

    private int code; // 성공과 실패를 저장하는 변수 : 1(성공), -1(실패)
    private String message;
    private T data;

}


