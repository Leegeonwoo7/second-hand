package com.secondhandplatform.service.user.response;

import lombok.Getter;

@Getter
public class IdCheckResponse {

    private boolean isDuplicate;
    private String message;

    public IdCheckResponse(boolean isDuplicate, String message) {
        this.isDuplicate = isDuplicate;
        this.message = message;
    }

    public static IdCheckResponse success() {
        return new IdCheckResponse( false, "Can use login ID");
    }

    public static IdCheckResponse duplicate() {
        return new IdCheckResponse(true, "Duplicate login ID");
    }

    public static IdCheckResponse databaseError() {
        return new IdCheckResponse(true, "Duplicate login ID");
    }
}
