package com.grace.book.http.response;

/**
 * Created by chenxb
 * 17/3/1.
 */

public class BookInfoResponse extends BaseResponse {
    private int IsCollect;
    private String BookId;
    private String BookName;
    private String BookTypeId;
    private String BookTypeName;
    private String Author;
    private String Pic;
    private String Price;
    private String Comment;
    private int TotalCount;
    private String InStoreTime;
    private String ChurchId;
    private String ChurchName;
    private int LockCount;
    private int CommendCount;
    private int CollectCount;
    private int ReadCount;
    private int BookState;//0-可借阅;1-已借阅;2-借阅数量上限;3-已借完

    public int getIsCollect() {
        return IsCollect;
    }

    public void setIsCollect(int IsCollect) {
        this.IsCollect = IsCollect;
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

    public String getComment() {
        return Comment;
    }

    public void setComment(String Comment) {
        this.Comment = Comment;
    }

    public int getTotalCount() {
        return TotalCount;
    }

    public void setTotalCount(int TotalCount) {
        this.TotalCount = TotalCount;
    }

    public String getInStoreTime() {
        return InStoreTime;
    }

    public void setInStoreTime(String InStoreTime) {
        this.InStoreTime = InStoreTime;
    }

    public String getChurchId() {
        return ChurchId;
    }

    public void setChurchId(String ChurchId) {
        this.ChurchId = ChurchId;
    }

    public String getChurchName() {
        return ChurchName;
    }

    public void setChurchName(String ChurchName) {
        this.ChurchName = ChurchName;
    }

    public int getLockCount() {
        return LockCount;
    }

    public void setLockCount(int LockCount) {
        this.LockCount = LockCount;
    }

    public int getCommendCount() {
        return CommendCount;
    }

    public void setCommendCount(int CommendCount) {
        this.CommendCount = CommendCount;
    }

    public int getCollectCount() {
        return CollectCount;
    }

    public void setCollectCount(int CollectCount) {
        this.CollectCount = CollectCount;
    }

    public int getReadCount() {
        return ReadCount;
    }

    public void setReadCount(int ReadCount) {
        this.ReadCount = ReadCount;
    }

    public int getBookState() {
        return BookState;
    }

    public void setBookState(int BookState) {
        this.BookState = BookState;
    }

    public String getBookStateText() {
        //0-可借阅;1-已借阅;2-借阅数量上限;3-已借完
        switch (BookState) {
            case 0:
                return "可借阅";
            case 1:
                return "已借阅";
            case 2:
                return "借阅数量上限";
            case 3:
                return "已借完";
        }
        return " - ";
    }

    public boolean canBorrow() {
        return BookState == 0;
    }

    public boolean haveBorrowed() {
        return BookState == 1;
    }

    public boolean isCollected() {
        return IsCollect == 1;
    }
}
