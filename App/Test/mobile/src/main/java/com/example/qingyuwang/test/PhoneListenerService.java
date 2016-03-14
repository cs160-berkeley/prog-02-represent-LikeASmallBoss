package com.example.qingyuwang.test;

import android.content.Intent;
import android.util.Log;

import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class PhoneListenerService extends WearableListenerService {

    private static final String Case1 = "/CandA";

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        Log.d("T", "in PhoneListenerService, got: " + messageEvent.getPath());

        if( messageEvent.getPath().equalsIgnoreCase( "/CANDIDATE" ) ) {



            String str = new String(messageEvent.getData(), StandardCharsets.UTF_8);

            Intent intent = new Intent(getBaseContext(), politician1.class);
            intent.putExtra("name", str);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            startActivity(intent);







//            String value = new String(messageEvent.getData(), StandardCharsets.UTF_8);
//            Intent intent = new Intent(this, politician1.class );
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            intent.putExtra("CANDIDATE", "A");
//            startActivity(intent);

        } else if (messageEvent.getPath().equalsIgnoreCase("/R")) {
            String value = new String(messageEvent.getData(), StandardCharsets.UTF_8);

            String random_zip = "99950";
            try {
                InputStream is = getAssets().open("listofzips.json");
                int size = is.available();
                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();
                JSONArray zips = new JSONArray(new String(buffer, "UTF-8"));
                int random_integer = (int) (Math.random() * zips.length());
                random_zip = zips.getString(random_integer);
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            Intent intent = new Intent(this, Main2Activity.class);
            intent.putExtra("R", random_zip);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            Log.d("DEBUGTAG", "about to start watch CandidatesView with random zipcode");
        }
    }
}
