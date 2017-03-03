package com.grace.book.event;

/**
 * Created by DuoNuo on 2017/3/4.
 */
public class CancelStarEvent {
    private String collectId;

    public CancelStarEvent(String collectId) {
        this.collectId = collectId;
    }

    public String getCollectId() {
        return collectId;
    }
}
