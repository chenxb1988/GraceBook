package com.grace.book.http.request;

/**
 * Created by DuoNuo on 2017/3/4.
 */
public class CancelStarRequest extends BaseRequest{

    private String collectId;
    private String authToken;

    public String getCollectId() {
        return collectId;
    }

    public void setCollectId(String collectId) {
        this.collectId = collectId;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }
}
