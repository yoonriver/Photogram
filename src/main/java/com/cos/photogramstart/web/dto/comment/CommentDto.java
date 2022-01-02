package com.cos.photogramstart.web.dto.comment;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

// NotNull = Null값 체크
// NotEmpty = 빈값이거나 null 체크
// NotBlank = 빈값이거나 null이거나 빈 공백(스페이스) 체크


@Data
public class CommentDto {
    @NotBlank // 빈값이거나 null이거나 빈 공백 체크
    private String content;

    @NotNull // null 체크
    private Integer imageId;

    // toEntity가 필요 없다
}
