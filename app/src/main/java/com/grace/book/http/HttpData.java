package com.grace.book.http;

/**
 * Created by chenxb on 17/2/13.
 */

public class HttpData {
    public static String URL = "http://120.27.201.161:4042/api/";

    public static String LOGIN = "userinfo/login";
    public static String LOGOUT = "userinfo/exitlogin";
    public static String USER_INFO = "userinfo/get";
    public static String USER_LIST = "userinfo/getlistbyrealname";

    public static String BOOK_LIST = "bookinfo/list";
    public static String BOOK_INFO = "bookinfo/get";
    public static String BOOK_BORROW = "bookinfo/borrowbook";
    public static String BOOK_COMMENTS = "bookinfo/getbookeavl";
    public static String BOOK_STAR = "bookborrow/collectbook";
    public static String BOOK_RECOMMEND = "bookborrow/commendbook";
    public static String BOOK_COMMENT = "bookborrow/evaluatebook";
    public static String BOOK_CALCEL_STAR = "bookborrow/cancelcollect";

    public static String MESSAGE_LIST = "message/list";

    public static String RECORD_LIST = "bookborrow/list";

    public static String FELLOW_LIST = "church/list";
    public static String GROUP_LIST = "churchgroup/listbychurchid";

    public static String UPLOAD_IMG = "userinfo/imgupload";
}
