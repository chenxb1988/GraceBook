package com.grace.book.http.request;

/**
 * Created by chenxb
 * 17/3/1.
 */

public class BaseBookRequest extends BaseRequest {
    private String bookId;
    private String authToken;

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }
}
