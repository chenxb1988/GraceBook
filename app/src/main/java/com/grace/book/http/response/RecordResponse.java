package com.grace.book.http.response;

import java.util.List;

/**
 * Created by chenxb on 2017/3/4.
 */
public class RecordResponse {

    private int TotalRecords;
    private int TotalPage;
    private List<RecordInfo> Records;

    public int getTotalRecords() {
        return TotalRecords;
    }

    public void setTotalRecords(int TotalRecords) {
        this.TotalRecords = TotalRecords;
    }

    public int getTotalPage() {
        return TotalPage;
    }

    public void setTotalPage(int TotalPage) {
        this.TotalPage = TotalPage;
    }

    public List<RecordInfo> getRecords() {
        return Records;
    }

    public void setRecords(List<RecordInfo> Records) {
        this.Records = Records;
    }

    public static class RecordInfo {

        private String UserId;
        private String BookId;
        private String BookName;
        private String BookTypeId;
        private String BookTypeName;
        private String Author;
        private String Pic;
        private String Price;
        private String BorrowTime;
        private String ReturnTime;
        private int IsCommend;
        private int IsCollect;
        private int IsEvaluate;
        private int IsBorrow;
        private int IsTimeout;
        private String CollectId;
        private String BorrowId;

        public String getUserId() {
            return UserId;
        }

        public void setUserId(String UserId) {
            this.UserId = UserId;
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

        public String getBookTypeId() {
            return BookTypeId;
        }

        public void setBookTypeId(String BookTypeId) {
            this.BookTypeId = BookTypeId;
        }

        public String getBookTypeName() {
            return BookTypeName;
        }

        public void setBookTypeName(String BookTypeName) {
            this.BookTypeName = BookTypeName;
        }

        public String getAuthor() {
            return Author;
        }

        public void setAuthor(String Author) {
            this.Author = Author;
        }

        public String getPic() {
            return Pic;
        }

        public void setPic(String Pic) {
            this.Pic = Pic;
        }

        public String getPrice() {
            return Price;
        }

        public void setPrice(String Price) {
            this.Price = Price;
        }

        public String getBorrowTime() {
            return BorrowTime;
        }

        public void setBorrowTime(String BorrowTime) {
            this.BorrowTime = BorrowTime;
        }

        public String getReturnTime() {
            return ReturnTime;
        }

        public void setReturnTime(String ReturnTime) {
            this.ReturnTime = ReturnTime;
        }

        public int getIsCommend() {
            return IsCommend;
        }

        public void setIsCommend(int IsCommend) {
            this.IsCommend = IsCommend;
        }

        public int getIsCollect() {
            return IsCollect;
        }

        public void setIsCollect(int IsCollect) {
            this.IsCollect = IsCollect;
        }

        public int getIsEvaluate() {
            return IsEvaluate;
        }

        public void setIsEvaluate(int IsEvaluate) {
            this.IsEvaluate = IsEvaluate;
        }

        public int getIsBorrow() {
            return IsBorrow;
        }

        public void setIsBorrow(int IsBorrow) {
            this.IsBorrow = IsBorrow;
        }

        public int getIsTimeout() {
            return IsTimeout;
        }

        public void setIsTimeout(int IsTimeout) {
            this.IsTimeout = IsTimeout;
        }

        public String getCollectId() {
            return CollectId;
        }

        public void setCollectId(String CollectId) {
            this.CollectId = CollectId;
        }

        public String getBorrowId() {
            return BorrowId;
        }

        public void setBorrowId(String borrowId) {
            BorrowId = borrowId;
        }

        public boolean isRecommended() {
            return IsCommend == 1;
        }

        public boolean isCommented() {
            return IsEvaluate == 1;
        }

        public boolean isBorrowing() {
            return IsBorrow == 1;
        }

        public boolean isCollected() {
            return IsCollect == 1;
        }

        public void setUnCollected() {
            IsCollect = 0;
        }

        public void setHasCollected() {
            IsCollect = 1;
        }

        public void setHasRecommend() {
            IsCommend = 1;
        }

        public void setHasComment(){
            IsEvaluate = 1;
        }
    }
}
