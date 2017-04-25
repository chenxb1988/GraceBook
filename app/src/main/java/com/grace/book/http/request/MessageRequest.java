package com.grace.book.http.request;

/**
 * Created by chenxb on 2017/4/24.
 */

public class MessageRequest extends BaseRequest {
    private String userId;
    private int pageSize;
    private int pageIndex;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }
}
