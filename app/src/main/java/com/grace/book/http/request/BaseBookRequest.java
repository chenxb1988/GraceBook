package com.grace.book.http.request;

/**
 * Created by chenxb
 * 17/3/1.
 */

public class BaseBookRequest extends BaseRequest {
    private String bookId;

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }
}
