package com.grace.book.utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.util.Date;

/**
 * Created by chenxb
 * 17/2/21.
 */

public class FileUtils {

    public static String getRootDir(Context ct) {
        String dir;
        if (checkSDCardAvailability()) {
            dir = Environment.getExternalStorageDirectory() + "/GraceBook";
        } else {
            dir = ct.getCacheDir() + "/GraceBook";
        }
        FileUtils.createDirectory(dir);
        return dir;
    }

    public static boolean createDirectory(String dir) {
        File f = new File(dir);
        if (f.exists()) {
            if (!f.isDirectory()) {
                f.delete();
            } else {
                return true;
            }
        }
        return f.mkdirs();
    }

    public static void deleteFile(String path) {
        try {
            File file = new File(path);
            file.deleteOnExit();
        } catch (Exception e) {

        }
    }

    public static boolean checkSDCardAvailability() {
        boolean result = false;
        try {
            Date now = new Date();
            long times = now.getTime();
            String fileName = Environment.getExternalStorageDirectory().toString() + File.separator + "delete" + times
                    + ".test";
            File file = new File(fileName);
            result = file.createNewFile();
            file.delete();
            result = true;
        } catch (Exception e) {
        }
        return result;
    }
}
