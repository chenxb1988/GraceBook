package com.grace.book.db.entity;

/**
 * Created by chenxb on 2017/4/24.
 */

public class MessageEntity {

    private String UserMessageId;
    private String UserId;
    private String UserName;
    private String MessageId;
    private String MessageType;
    private String SendUserId;
    private String SendUserName;
    private String MessageTitle;
    private String MessageContent;
    private int Status;
    private String UpdateTime;

    public String getUserMessageId() {
        return UserMessageId;
    }

    public void setUserMessageId(String UserMessageId) {
        this.UserMessageId = UserMessageId;
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

    public String getMessageId() {
        return MessageId;
    }

    public void setMessageId(String MessageId) {
        this.MessageId = MessageId;
    }

    public String getMessageType() {
        return MessageType;
    }

    public void setMessageType(String MessageType) {
        this.MessageType = MessageType;
    }

    public String getSendUserId() {
        return SendUserId;
    }

    public void setSendUserId(String SendUserId) {
        this.SendUserId = SendUserId;
    }

    public String getSendUserName() {
        return SendUserName;
    }

    public void setSendUserName(String SendUserName) {
        this.SendUserName = SendUserName;
    }

    public String getMessageTitle() {
        return MessageTitle;
    }

    public void setMessageTitle(String MessageTitle) {
        this.MessageTitle = MessageTitle;
    }

    public String getMessageContent() {
        return MessageContent;
    }

    public void setMessageContent(String MessageContent) {
        this.MessageContent = MessageContent;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int Status) {
        this.Status = Status;
    }

    public String getUpdateTime() {
        return UpdateTime;
    }

    public void setUpdateTime(String UpdateTime) {
        this.UpdateTime = UpdateTime;
    }
}
