package com.grace.book.http.request;

/**
 * Created by DuoNuo on 2017/2/17.
 */
public class BaseTokenRequest extends BaseRequest{
    private String authToken;

    public BaseTokenRequest(String authToken) {
        super();
        this.authToken = authToken;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }
}
