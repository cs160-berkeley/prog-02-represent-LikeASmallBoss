package com.example.qingyuwang.test;

import android.app.Activity;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.util.Log;
import android.widget.TextView;

public class election2012 extends Activity {

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_election2012);

        TextView zip = (TextView) findViewById(R.id.textView3);
        zip.setText("Zipcode: " + Integer.toString((((Helper) this.getApplication()).getZip())));

    }


}
