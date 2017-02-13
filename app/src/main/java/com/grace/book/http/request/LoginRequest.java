package com.grace.book.http.request;

/**
 * Created by chenxb on 17/2/13.
 */

public class LoginRequest extends BaseRequest {
    private String mobile;
    private String password;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
