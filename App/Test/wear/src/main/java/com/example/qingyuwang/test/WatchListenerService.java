package com.example.qingyuwang.test;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;


import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;


public class WatchListenerService extends WearableListenerService {

    private static final String Case1 = "/CandA";

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        Log.d("T", "in WatchListenerService, got: " + messageEvent.getPath());

        if (messageEvent.getPath().equalsIgnoreCase(Case1)) {
            Intent intent = new Intent(this, Main2Activity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

        } else if (messageEvent.getPath().startsWith("/zip")) {
            int newzip = Integer.parseInt(messageEvent.getPath().substring(4));
            ((Helper) this.getApplication()).setZip(newzip);
            Intent intent = new Intent(this, Main2Activity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            Toast.makeText(this, "Zipcode: " + Integer.toString(newzip), Toast.LENGTH_LONG).show();
        }

    }
}
