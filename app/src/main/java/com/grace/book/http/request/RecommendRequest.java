package com.grace.book.http.request;

/**
 * Created by DuoNuo on 2017/3/5.
 */
public class RecommendRequest extends BaseRequest {

    private String borrowId;
    private String authToken;

    public String getBorrowId() {
        return borrowId;
    }

    public void setBorrowId(String borrowId) {
        this.borrowId = borrowId;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }
}
