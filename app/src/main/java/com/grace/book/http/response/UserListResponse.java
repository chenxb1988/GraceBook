package com.grace.book.http.response;

import java.util.List;

/**
 * Created by chenxb on 2017/4/25.
 */

public class UserListResponse extends BaseResponse {
    List<UserInfo> Records;

    public List<UserInfo> getRecords() {
        return Records;
    }

    public void setRecords(List<UserInfo> records) {
        Records = records;
    }
}
