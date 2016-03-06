package com.example.qingyuwang.test;

import android.app.Application;

public class Helper extends Application {

    private int code = 94702;

    public int getZip() {
        return code;
    }

    public void setZip(int zip) {
        code = zip;
    }
}
