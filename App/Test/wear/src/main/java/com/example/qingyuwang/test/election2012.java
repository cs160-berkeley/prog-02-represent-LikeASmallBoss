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



        String[] data = ((Helper) this.getApplication()).getData();
        TextView romney = (TextView) findViewById(R.id.textView7);
        TextView obama = (TextView) findViewById(R.id.textView6);

        Double romney_double = (Double.parseDouble(data[data.length - 2])/10);
        Double obama_double = (Double.parseDouble(data[data.length - 1])/10);
        romney.setText(romney_double.toString() + "%");
        obama.setText(obama_double.toString() + "%");





    }


}
