package com.grace.book.http.request;

/**
 * Created by chenxb
 * 17/2/23.
 */

public class UploadImgRequest extends BaseRequest {
    private String file;

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }
}
