package com.grace.book.db;

import com.grace.book.utils.SharedUtils;

/**
 * Created by chenxb on 2017/4/24.
 */

public class DBMessage {
    public static final String TABLE = "message_";

    public static final String UserMessageId = "UserMessageId";
    public static final String UserId = "UserId";
    public static final String UserName = "UserName";
    public static final String MessageId = "MessageId";
    public static final String MessageType = "MessageType";//1000-系统公告,1100-借阅提醒,1200-逾期通知
    public static final String SendUserId = "SendUserId";//0000-系统
    public static final String SendUserName = "SendUserName";
    public static final String MessageTitle = "MessageTitle";
    public static final String MessageContent = "MessageContent";
    public static final String Status = "Status";
    public static final String UpdateTime = "UpdateTime";

    public static String getCreateSql() {
        String sql = "CREATE TABLE IF NOT EXISTS "
                + getTableName() + " ("
                + UserMessageId + " TEXT, "
                + UserId + " TEXT, "
                + UserName + " TEXT, "
                + MessageId + " TEXT)"
                + MessageType + " TEXT)"
                + SendUserId + " TEXT)"
                + SendUserName + " TEXT)"
                + MessageTitle + " TEXT)"
                + MessageContent + " TEXT)"
                + Status + " TEXT)"
                + UpdateTime + " TEXT)";
        return sql;
    }

    public static String getTableName() {
        return TABLE + SharedUtils.getUserId();
    }
}
