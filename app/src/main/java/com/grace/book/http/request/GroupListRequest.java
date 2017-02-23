package com.grace.book.http.request;

/**
 * Created by chenxb
 * 17/2/23.
 */

public class GroupListRequest extends BaseRequest {
    private String churchId;
    private String authToken;

    public String getChurchId() {
        return churchId;
    }

    public void setChurchId(String churchId) {
        this.churchId = churchId;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }
}
