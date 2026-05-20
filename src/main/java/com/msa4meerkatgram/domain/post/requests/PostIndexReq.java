package com.msa4meerkatgram.domain.post.requests;

public record PostIndexReq(
        Integer page,
        Integer limit
) {
    public PostIndexReq(Integer page, Integer limit) {
        this.page = (page != null && page > 0) ? page : 1;
        this.limit = (limit != null && limit > 0) ? limit : 6;

    }
}
