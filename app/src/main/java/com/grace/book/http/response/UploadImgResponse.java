package com.grace.book.http.response;

import java.util.List;

/**
 * Created by chenxb
 * 17/2/23.
 */

public class UploadImgResponse extends BaseResponse {

    private List<FileAddress> FileList;

    public List<FileAddress> getFileList() {
        return FileList;
    }

    public void setFileList(List<FileAddress> FileList) {
        this.FileList = FileList;
    }

    public static class FileAddress {
        private String OutFileAddress;
        private String ThumbnailAddress;

        public String getOutFileAddress() {
            return OutFileAddress;
        }

        public void setOutFileAddress(String OutFileAddress) {
            this.OutFileAddress = OutFileAddress;
        }

        public String getThumbnailAddress() {
            return ThumbnailAddress;
        }

        public void setThumbnailAddress(String ThumbnailAddress) {
            this.ThumbnailAddress = ThumbnailAddress;
        }
    }
}
