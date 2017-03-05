package com.grace.book.event;

/**
 * Created by DuoNuo on 2017/3/5.
 */
public class AddStarEvent {
    private String bookId;

    public AddStarEvent(String bookId) {
        this.bookId = bookId;
    }

    public String getBookId() {
        return bookId;
    }
}
