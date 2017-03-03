package com.grace.book.http.response;

import java.util.List;

/**
 * Created by chenxb on 2017/3/3.
 */
public class BookCommentResponse {
    private List<BookComment> Records;

    public List<BookComment> getRecords() {
        return Records;
    }

    public void setRecords(List<BookComment> Records) {
        this.Records = Records;
    }

    public static class BookComment {

        private String EvaluateId;
        private String UserId;
        private String UserName;
        private String Avatar;
        private String BookId;
        private String BookName;
        private String Message;
        private String UpdateTime;

        public String getEvaluateId() {
            return EvaluateId;
        }

        public void setEvaluateId(String EvaluateId) {
            this.EvaluateId = EvaluateId;
        }

        public String getUserId() {
            return UserId;
        }

        public void setUserId(String UserId) {
            this.UserId = UserId;
        }

        public String getUserName() {
            return UserName;
        }

        public void setUserName(String UserName) {
            this.UserName = UserName;
        }

        public String getAvatar() {
            return Avatar;
        }

        public void setAvatar(String Avatar) {
            this.Avatar = Avatar;
        }

        public String getBookId() {
            return BookId;
        }

        public void setBookId(String BookId) {
            this.BookId = BookId;
        }

        public String getBookName() {
            return BookName;
        }

        public void setBookName(String BookName) {
            this.BookName = BookName;
        }

        public String getMessage() {
            return Message;
        }

        public void setMessage(String Message) {
            this.Message = Message;
        }

        public String getUpdateTime() {
            return UpdateTime;
        }

        public void setUpdateTime(String UpdateTime) {
            this.UpdateTime = UpdateTime;
        }
    }
}
