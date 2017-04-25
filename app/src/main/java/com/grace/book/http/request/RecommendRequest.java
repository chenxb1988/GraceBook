package com.grace.book.http.request;

/**
 * Created by DuoNuo on 2017/3/5.
 */
public class RecommendRequest extends BaseRequest {

    private String borrowId;

    public String getBorrowId() {
        return borrowId;
    }

    public void setBorrowId(String borrowId) {
        this.borrowId = borrowId;
    }
}
