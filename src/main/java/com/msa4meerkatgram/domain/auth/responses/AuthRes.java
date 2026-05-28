package com.msa4meerkatgram.domain.auth.responses;

import com.msa4meerkatgram.domain.user.response.UserRes;
import lombok.Builder;

@Builder
public record AuthRes(
        UserRes user
        , String accessToken
) {
}
