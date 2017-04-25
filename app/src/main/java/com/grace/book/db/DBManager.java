package com.grace.book.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.grace.book.MyApplication;
import com.grace.book.db.entity.MessageEntity;


/**
 * Created by chenxb on 2017/1/13.
 */
public class DBManager {
    private SQLiteDatabase db;
    private DBHelper dbHelper;

    public DBManager() {
        dbHelper = new DBHelper(MyApplication.getContext());
    }

    /**
     * 插入缓存
     *
     * @param url  地址
     * @param data json数据
     */
    public synchronized void insertData(String url, String data) {
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBHelper.URL, url);
        values.put(DBHelper.DATA, data);
        values.put(DBHelper.TIME, System.currentTimeMillis());
        db.replace(DBHelper.CACHE, null, values);
        db.close();
    }

    /**
     * 根据url获取缓存数据
     *
     * @param url 地址
     * @return 数据
     */
    public synchronized String getData(String url) {
        String result = "";
        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DBHelper.CACHE + " WHERE URL = ?", new String[]{url});
        while (cursor.moveToNext()) {
            result = cursor.getString(cursor.getColumnIndex(DBHelper.DATA));
        }
        cursor.close();
        db.close();
        return result;
    }

    public void createMessageTable() {
        db = dbHelper.getWritableDatabase();
        db.execSQL(DBMessage.getCreateSql());
    }

    public synchronized void insertMessage(MessageEntity message) {
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBMessage.UserMessageId, message.getUserMessageId());
        values.put(DBMessage.UserId, message.getUserId());
        values.put(DBMessage.UserName, message.getUserName());
        values.put(DBMessage.MessageId, message.getMessageId());
        values.put(DBMessage.MessageType, message.getMessageType());
        values.put(DBMessage.SendUserId, message.getSendUserId());
        values.put(DBMessage.SendUserName, message.getSendUserName());
        values.put(DBMessage.MessageTitle, message.getMessageTitle());
        values.put(DBMessage.MessageContent, message.getMessageContent());
        values.put(DBMessage.Status, message.getStatus());
        values.put(DBMessage.UpdateTime, message.getUpdateTime());

        db.replace(DBMessage.getTableName(), null, values);
        db.close();
    }

    public MessageEntity getMessage(String id) {
        db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DBMessage.getTableName() + " WHERE " + DBMessage.UserMessageId + " = ?", new String[]{id});
        MessageEntity message = new MessageEntity();
        try {
            message.setSendUserId(id);
            message.setUserId(cursor.getString(cursor.getColumnIndex(DBMessage.UserId)));
            message.setUserName(cursor.getString(cursor.getColumnIndex(DBMessage.UserName)));
            message.setMessageId(cursor.getString(cursor.getColumnIndex(DBMessage.MessageId)));
            message.setMessageType(cursor.getString(cursor.getColumnIndex(DBMessage.MessageType)));
            message.setSendUserId(cursor.getString(cursor.getColumnIndex(DBMessage.SendUserId)));
            message.setSendUserName(cursor.getString(cursor.getColumnIndex(DBMessage.SendUserName)));
            message.setMessageTitle(cursor.getString(cursor.getColumnIndex(DBMessage.MessageTitle)));
            message.setMessageContent(cursor.getString(cursor.getColumnIndex(DBMessage.MessageContent)));
            message.setStatus(Integer.parseInt(cursor.getString(cursor.getColumnIndex(DBMessage.Status))));
            message.setUpdateTime(cursor.getString(cursor.getColumnIndex(DBMessage.UpdateTime)));
        } catch (Exception ex) {

        } finally {
            cursor.close();
            db.close();
        }
        return message;
    }

    public synchronized void setMessageRead(MessageEntity message) {
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBMessage.Status, 1);

        db.update(DBMessage.getTableName(), values, DBMessage.UserMessageId + " = ?", new String[]{message.getUserMessageId()});
        db.close();
    }

    public synchronized void deleteMessage(MessageEntity message) {
        db = dbHelper.getWritableDatabase();
        db.delete(DBMessage.getTableName(), DBMessage.UserMessageId + " = ?", new String[]{message.getUserMessageId()});
    }
}
