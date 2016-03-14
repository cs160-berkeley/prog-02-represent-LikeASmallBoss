package com.example.qingyuwang.test;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


public class Main2Activity extends AppCompatActivity {

    private String auth_key = "d241cb20ce504473b792875a573dea4d";
    private String google_auth_key = "AIzaSyCQvutc1xrmbVWQ759edbD8q8nGaRHI1WU";
    private String zipcode;
    private TextView name;
    private LinearLayout panels;
    private LayoutInflater inflater;
    private ArrayList<RepBio> rep_bios = new ArrayList<RepBio>();
    private String lat;
    private String lon;
    private String btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        panels = (LinearLayout) findViewById(R.id.panels);
        inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
        name = (TextView) findViewById(R.id.textView4);
        zipcode = Helper.getZip();
        btn = Helper.getBtn();


        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        if (extras != null && extras.getString("R") != null) {
            zipcode = extras.getString("R");
            btn = "zip";
        }

        try {
            decodeJSONRepInfo(getRepresentatives(btn));
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public String getRepresentatives(String str) throws
            ExecutionException, InterruptedException {
        if (str.equals("zip")) {
            Log.d("DEBUGTAG", "getRepresentatives(string) called with " + zipcode);
            Request Req = new Request();
            return Req.execute("http://congress.api.sunlightfoundation.com/legislators/locate?zip="
                    + zipcode + "&apikey=" + auth_key).get();
        }
        if (str.equals("gps")) {
            Log.d("DEBUGTAG", "getRepresentatives(double) called with " + Helper.getLati()+ ", " + Helper.getLongti());
            Request Req = new Request();
            return Req.execute("http://congress.api.sunlightfoundation.com/legislators/locate?latitude="
                    + Helper.getLati() + "&longitude="
                    + Helper.getLongti() + "&apikey=" + auth_key).get();
        }
        else{
            Log.d("DEBUGTAG", "getRepresentatives(else) called");
            Request Req = new Request();
            return Req.execute("http://congress.api.sunlightfoundation.com/legislators/locate?zip="
                    + "72201" + "&apikey=" + auth_key).get();
        }
    }





    public String getLocation(String zip) throws ExecutionException, InterruptedException {
        Log.d("DEBUGTAG", "getLocation: " + zip);

        Request req = new Request();
        return req.execute("https://maps.googleapis.com/maps/api/geocode/json?address="
                + zip + ",united+states&key=" + google_auth_key).get();
    }



    private void decodeJSONaddress(String addressJSON0, String representatives_info) {

        try {
            JSONObject addressJSON = new JSONObject(addressJSON0);
            Log.d("DEBUGTAG", "try decoding json with parse object");
            JSONArray address_components = addressJSON.getJSONArray("results")
                    .getJSONObject(0)
                    .getJSONArray("address_components");
            int array_length = address_components.length();


            String county = "";
            String state = "";
            String zip = "";

            for (int i = 0; i < array_length; i++) {
                String temp = address_components.getJSONObject(i).
                        getJSONArray("types").
                        toString();
                if (temp.contains("\"administrative_area_level_2\"")) {
                    county = address_components
                            .getJSONObject(i).get("short_name").toString();
                } else if (temp.contains("\"administrative_area_level_1\"")) {
                    state = address_components
                            .getJSONObject(i).get("short_name").toString();
                } else if (temp.contains("\"postal_code\"")) {
                    zip = address_components
                            .getJSONObject(i).get("short_name").toString();
                }
            }

            JSONObject vote2012_JSON_object = null;
            String romney = "000";
            String obama = "000";


            String vote2012_string = null;
            try {
                InputStream is = getAssets().open("electionresult.json");
                int size = is.available();
                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();
                vote2012_string = new String(buffer, "UTF-8");
            } catch (IOException ex) {
                ex.printStackTrace();
            }


            try {
                vote2012_JSON_object = new JSONObject(vote2012_string);
                JSONObject result = vote2012_JSON_object.getJSONObject(county + ", " + state);
                romney = String.valueOf((int) (result.getDouble("romney")*10));
                obama = String.valueOf((int) (result.getDouble("obama")*10));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.d("DEBUGTAG", "romney=" + romney + " obama=" + obama + " " + county + ", " + state);

            representatives_info = representatives_info + ";" + romney + ";" + obama;
            Log.d("TAG", representatives_info);
            Intent intent = new Intent(getBaseContext(), PhoneToWatchService.class);
            intent.putExtra("Info", representatives_info);
            startService(intent);





        } catch (JSONException e) {
            e.printStackTrace();
        }

    }












    private void decodeJSONRepInfo(String repsJSON0) {

        try {
            JSONObject repsJSON = new JSONObject(repsJSON0);
            JSONArray repsArray = repsJSON.getJSONArray("results");
            int count = repsJSON.getInt("count");

            String representatives_info = "";

            for (int i = 0; i < count; i++) {
                JSONObject repJSON = repsArray.getJSONObject(i);

                RepBio new_rep = new RepBio(repJSON.getString("bioguide_id"));
                new_rep.rep_name = repJSON.getString("first_name")
                        + " " + repJSON.getString("last_name");
                new_rep.party = partyParser(repJSON.getString("party"));
                new_rep.oc_email = repJSON.getString("oc_email");
                new_rep.twitter_id = repJSON.getString("twitter_id");
                new_rep.website = repJSON.getString("website");
                addPanel(new_rep);
                rep_bios.add(new_rep);



                representatives_info += new_rep.rep_name;
                representatives_info += ";";
                representatives_info += new_rep.party;
                representatives_info += ";";
            }
            representatives_info += zipcode;


            decodeJSONaddress(getLocation(zipcode), representatives_info);



//            Intent intent = new Intent(getBaseContext(), PhoneToWatchService.class);
//            intent.putExtra("Info", representatives_info);
//            startService(intent);


        } catch (JSONException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }


    private String partyParser(String party0) {
        if (party0.equals("D")) {
            return "Democratic";
        } else if (party0.equals("R")) {
            return "Republican";
        } else {
            return "Independent";
        }
    }


    private void addPanel(RepBio rep_bio) {
        final String str = rep_bio.rep_name;
        ViewGroup panel = (ViewGroup) inflater.inflate(R.layout.candidate_info, null);
        panels.addView(panel);
        ((TextView) panel.findViewById(R.id.rep_name)).setText(rep_bio.rep_name);
        ((TextView) panel.findViewById(R.id.rep_party)).setText(rep_bio.party);
        ((TextView) panel.findViewById(R.id.rep_email)).setText(rep_bio.oc_email);
        ((TextView) panel.findViewById(R.id.rep_website)).setText(rep_bio.website);
        ((TextView) panel.findViewById(R.id.term_ends_date)).setText(rep_bio.term_ends_date + "\n");
        rep_bio.panel = panel;
        Button button = (Button) panel.findViewById(R.id.button32);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), politician1.class);
                intent.putExtra("name", str);
                startActivity(intent);
            }
        });
    }

}
