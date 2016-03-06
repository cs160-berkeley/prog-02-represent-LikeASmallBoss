package com.example.qingyuwang.test;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.view.View;
import android.widget.TextView;

public class Main2Activity extends Activity {

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                mTextView = (TextView) stub.findViewById(R.id.text);
            }
        });

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            int zipcode = extras.getInt("ZIP");
            ((Helper) this.getApplication()).setZip(zipcode);
        }
    }

    public void toelection(View view){
        Intent intent = new Intent(this, election2012.class);
        startActivity(intent);}

    public void toreps(View view){
        Intent intent = new Intent(this, reps.class);
        startActivity(intent);}


}
