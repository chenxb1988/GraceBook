package com.grace.book.http.request;

/**
 * Created by chenxb
 * 17/2/22.
 */

public class FellowListRequest extends BaseRequest {
    private String churchId;

    public String getChurchId() {
        return churchId;
    }

    public void setChurchId(String churchId) {
        this.churchId = churchId;
    }
}
