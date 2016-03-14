package com.example.qingyuwang.test;

import android.view.View;

/**
 * Created by qingyuwang on 3/10/16.
 */
public class Helper {
    private static String code = "94704";
    private static String btn = "zip";

    public static String getZip() {
        return code;
    }

    public static void setZip(String zip) {
        code = zip;
    }


    private static String lati;
    private static String longti;

    public static void setLati(String str) {
        lati = str;
    }

    public static void setLongti(String str) {
        longti = str;
    }

    public static String getLongti() {
        return longti;
    }

    public static String getLati() {
        return lati;
    }

    public static void setBtn(String str) {
        btn = str;
    }


    public static String getBtn() {
        return btn;
    }

    public static void setZip() {
    }
}
