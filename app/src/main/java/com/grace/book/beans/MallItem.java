package com.grace.book.beans;

/**
 * Created by gjz on 18/01/2017.
 */

public class MallItem {
    private int imgResId;
    private String title;
    private int likes;
    private int comments;

    public MallItem(int imgResId, String title, int likes, int comments) {
        this.imgResId = imgResId;
        this.title = title;
        this.likes = likes;
        this.comments = comments;
    }

    public int getImgResId() {
        return imgResId;
    }

    public String getTitle() {
        return title;
    }

    public int getLikes() {
        return likes;
    }

    public int getComments() {
        return comments;
    }
}
