package com.msa4meerkatgram.domain.user.response;

import lombok.Builder;

@Builder
public record UserRes(
        long id
        ,String email
        ,String nick
        ,String role
        ,String profile
        ,String createdAt
        ,long countPosts
) {
}
