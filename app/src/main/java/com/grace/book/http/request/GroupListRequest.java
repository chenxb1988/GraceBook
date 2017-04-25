package com.grace.book.http.request;

/**
 * Created by chenxb
 * 17/2/23.
 */

public class GroupListRequest extends BaseRequest {
    private String churchId;
    private int pageIndex;
    private int pageSize;

    public String getChurchId() {
        return churchId;
    }

    public void setChurchId(String churchId) {
        this.churchId = churchId;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
