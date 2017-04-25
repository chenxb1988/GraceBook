package com.grace.book.http.request;

/**
 * Created by chenxb on 2017/4/24.
 */

public class CommentRequest extends BaseRequest {
    private String bookId;
    private String msg;

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
