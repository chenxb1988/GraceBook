package com.grace.book.event;

/**
 * Created by DuoNuo on 2017/3/4.
 */
public class CancelBorrowEvent {
    private String borrowId;

    public CancelBorrowEvent(String borrowId) {
        this.borrowId = borrowId;
    }

    public String getBorrowId() {
        return borrowId;
    }
}
