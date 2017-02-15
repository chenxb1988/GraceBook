package com.grace.book.event;

import com.grace.book.entity.LoginInfo;

/**
 * Created by chenxb
 * 17/2/15.
 */
public class LoginEvent {
    private LoginInfo loginInfo;

    public LoginEvent(LoginInfo result) {
        loginInfo = result;
    }

    public LoginInfo getLoginInfo() {
        return loginInfo;
    }
}
