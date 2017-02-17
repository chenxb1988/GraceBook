package com.grace.book.http.response;

/**
 * Created by chenxb
 * 17/2/17.
 */

public class UserInfo {

    private String UserId;
    private String UserName;
    private String RealName;
    private String Mobile;
    private String Avatar;
    private int Gender;
    private String Email;
    private String Birthday;
    private String GroupId;
    private String GroupName;

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

    public String getRealName() {
        return RealName;
    }

    public void setRealName(String RealName) {
        this.RealName = RealName;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String Mobile) {
        this.Mobile = Mobile;
    }

    public String getAvatar() {
        return Avatar;
    }

    public void setAvatar(String Avatar) {
        this.Avatar = Avatar;
    }

    public int getGender() {
        return Gender;
    }

    public void setGender(int Gender) {
        this.Gender = Gender;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getBirthday() {
        return Birthday;
    }

    public void setBirthday(String Birthday) {
        this.Birthday = Birthday;
    }

    public String getGroupId() {
        return GroupId;
    }

    public void setGroupId(String GroupId) {
        this.GroupId = GroupId;
    }

    public String getGroupName() {
        return GroupName;
    }

    public void setGroupName(String GroupName) {
        this.GroupName = GroupName;
    }
}
