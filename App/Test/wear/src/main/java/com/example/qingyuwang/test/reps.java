package com.example.qingyuwang.test;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class reps extends Activity {

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reps);
//        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
//        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
//            @Override
//            public void onLayoutInflated(WatchViewStub stub) {
//                mTextView = (TextView) stub.findViewById(R.id.text);
//            }
//        });





//        String[] data = Helper.getData();
        String[] data = ((Helper) this.getApplication()).getData();



        ((TextView) findViewById(R.id.firstname)).setText(data[0]);
        ((TextView) findViewById(R.id.firstparty)).setText(data[1]);
        ((TextView) findViewById(R.id.secondname)).setText(data[2]);
        ((TextView) findViewById(R.id.secondparty)).setText(data[3]);
        ((TextView) findViewById(R.id.thirdname)).setText(data[4]);
        ((TextView) findViewById(R.id.thirdparty)).setText(data[5]);


        final String button1link = data[0];
        Button button1 = (Button) findViewById(R.id.btn1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("T", "Watch button is clicked");
                Intent sendIntent = new Intent(getBaseContext(), WatchToPhoneService.class);
                sendIntent.putExtra("CANDIDATE", button1link);
                startService(sendIntent);
            }
        });

        final String button2link = data[2];
        Button button2 = (Button) findViewById(R.id.btn2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("T", "Watch button is clicked");
                Intent sendIntent = new Intent(getBaseContext(), WatchToPhoneService.class);
                sendIntent.putExtra("CANDIDATE", button2link);
                startService(sendIntent);
            }
        });

        final String button3link = data[4];
        Button button3 = (Button) findViewById(R.id.btn3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("T", "Watch button is clicked");
                Intent sendIntent = new Intent(getBaseContext(), WatchToPhoneService.class);
                sendIntent.putExtra("CANDIDATE", button3link);
                startService(sendIntent);
            }
        });







    }

//    public void toact1(View view){
//        Log.d("T", "Watch button is clicked");
//        Intent sendIntent = new Intent(getBaseContext(), WatchToPhoneService.class);
//        sendIntent.putExtra("CANDIDATE", "1");
//        startService(sendIntent);
//    }
//
//    public void toact2(View view){
//        Log.d("T", "Watch button is clicked");
//        Intent sendIntent = new Intent(getBaseContext(), WatchToPhoneService.class);
//        sendIntent.putExtra("CANDIDATE", "2");
//        startService(sendIntent);
//    }
//
//    public void toact3(View view){
//        Log.d("T", "Watch button is clicked");
//        Intent sendIntent = new Intent(getBaseContext(), WatchToPhoneService.class);
//        sendIntent.putExtra("CANDIDATE", "3");
//        startService(sendIntent);
//    }


}
