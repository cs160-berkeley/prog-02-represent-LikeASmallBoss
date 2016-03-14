package com.example.qingyuwang.test;

import android.graphics.Bitmap;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by qingyuwang on 3/11/16.
 */
public class RepBio {

    public String rep_name;
    public String party;
    public String bioguide_id;
    public String oc_email;
    public String website;
    public String term_ends_date;
    public String house;

    public String twitter_id;
    public String last_twit;
    public String last_twit_time;
    public String twitter_picture_link;
    public Bitmap twitter_picture;

    public ArrayList<String> committees;
    public ArrayList<String> bills;

    public View panel;

    public RepBio(String bio_id) {
        bioguide_id = bio_id;
        committees = new ArrayList<String>();
        bills = new ArrayList<String>();
    }
}
