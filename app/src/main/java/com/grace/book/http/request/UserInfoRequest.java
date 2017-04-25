package com.grace.book.http.request;

/**
 * Created by chenxb
 * 17/2/17.
 */

public class UserInfoRequest extends BaseRequest {

    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
