package com.example.qingyuwang.test;

import android.app.Application;

public class Helper extends Application {
    private static String[] data;
    private static int code;


    public static String[] getData() {
        return data;
    }

    public static void setData(String[] arry) {
        data = arry;
        code = Integer.parseInt(data[data.length-3]);
    }




    public static int getZip() {
        return code;
    }
}
