package com.msa4meerkatgram.domain.post.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PostCreateReq(
    @NotBlank(message = "내용을 입력해주세요.")
    @Size(max = 200, message = "내용은 200자 이내로 입력해주세요.")
    String content,

    @NotBlank(message = "이미지 경로가 누락되었습니다.")
    String image
) {
}
