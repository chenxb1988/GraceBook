package com.grace.book.event;

import com.grace.book.http.response.UserInfo;

/**
 * Created by chenxb
 * 20/2/15.
 */
public class UserEditEvent {
    private UserInfo userInfo;

    public UserEditEvent(UserInfo result) {
        userInfo = result;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }
}
