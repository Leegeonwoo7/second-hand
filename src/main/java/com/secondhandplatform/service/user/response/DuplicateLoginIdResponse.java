package com.secondhandplatform.service.user.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class DuplicateLoginIdResponse {

    private boolean duplicate;

    @Builder
    private DuplicateLoginIdResponse(boolean duplicate) {
        this.duplicate = duplicate;
    }

    public static DuplicateLoginIdResponse isDuplicate() {
        return DuplicateLoginIdResponse.
                builder()
                .duplicate(true)
                .build();
    }

    public static DuplicateLoginIdResponse isNotDuplicate() {
        return DuplicateLoginIdResponse
                .builder()
                .duplicate(false)
                .build();
    }

    public boolean getDuplicate() {
        return this.duplicate;
    }
}
