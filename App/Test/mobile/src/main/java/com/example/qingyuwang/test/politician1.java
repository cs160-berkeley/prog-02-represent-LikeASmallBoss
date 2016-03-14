//package com.example.qingyuwang.test;
//
//import android.os.Bundle;
//import android.support.design.widget.FloatingActionButton;
//import android.support.design.widget.Snackbar;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.Toolbar;
//import android.view.View;
//
//public class politician1 extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_politician1);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//    }
//
//}

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


public class politician1 extends AppCompatActivity {

    private String auth_key = "d241cb20ce504473b792875a573dea4d";
    private String zipcode;

    private TextView name;
    private TextView party;
    private TextView emailad;
    private TextView website;
    private TextView end;
    private TextView committe;
    private TextView bill;

    private LinearLayout panels;
    private LayoutInflater inflater;
    private ArrayList<RepBio> rep_bios = new ArrayList<RepBio>();
    private String lat;
    private String lon;
    private String btn;
    private String email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_politician1);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        email = extras.getString("name");

        name = (TextView) findViewById(R.id.textView4);
        party = (TextView) findViewById(R.id.textView5);
        emailad = (TextView) findViewById(R.id.textView3);
        website = (TextView) findViewById(R.id.textView6);
        end = (TextView) findViewById(R.id.textView22);
        committe = (TextView) findViewById(R.id.textView1232);
        bill = (TextView) findViewById(R.id.textView134);


        zipcode = Helper.getZip();
        btn = Helper.getBtn();
        try {
            decodeJSONRepInfo(getRepresentatives(btn));
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    //    public String getRepresentatives() throws ExecutionException, InterruptedException {
//        Log.d("DEBUGTAG", "getRepresentatives(double) called");
//        RequestClient hey = new RequestClient();
//        return hey.execute("http://congress.api.sunlightfoundation.com/legislators/locate?latitude="
//                + Helper.getLati() + "&longitude="
//                + Helper.getLongti() + "&apikey=" + auth_key).get();
//    }
    public String getRepresentatives(String str) throws
            ExecutionException, InterruptedException {
        if (str == "zip") {
            Log.d("DEBUGTAG", "getRepresentatives(string) called with " + zipcode);
            Request Req = new Request();
            return Req.execute("http://congress.api.sunlightfoundation.com/legislators/locate?zip="
                    + zipcode + "&apikey=" + auth_key).get();
        }
        if (str == "gps") {
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


    private void decodeJSONRepInfo(String repsJSON0) {

        try {
            JSONObject repsJSON = new JSONObject(repsJSON0);

            Log.d("DEBUGTAG", "try decoding representative information");
            JSONArray repsArray = repsJSON.getJSONArray("results");
            int count = repsJSON.getInt("count");
            Log.d("DEBUGTAG", String.valueOf(count) + " representatives found");

            for (int i = 0; i < count; i++) {
                JSONObject repJSON = repsArray.getJSONObject(i);
                RepBio new_rep = new RepBio(repJSON.getString("bioguide_id"));

                String full_name = repJSON.getString("first_name")
                        + " " + repJSON.getString("last_name");


                if (full_name.equals(email)) {
                    new_rep.rep_name = repJSON.getString("first_name")
                            + " " + repJSON.getString("last_name");
                    new_rep.party = partyParser(repJSON.getString("party"));
                    new_rep.oc_email = repJSON.getString("oc_email");
                    new_rep.term_ends_date = repJSON.getString("term_end");
                    new_rep.website = repJSON.getString("website");

                    decodeJSONBills(getBills(new_rep.bioguide_id), new_rep);
                    decodeJSONCommittees(getCommittees(new_rep.bioguide_id), new_rep);


                    set(new_rep);

                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }


    public String getBills(String bioguide_id) throws ExecutionException, InterruptedException {
        Log.d("DEBUGTAG", "getBills(string) called with " + bioguide_id);
        Request hey = new Request();
        return hey.execute("http://congress.api.sunlightfoundation.com/bills?sponsor_id="
                + bioguide_id + "&apikey=" + auth_key).get();
    }

    public String getCommittees(String bioguide_id) throws ExecutionException, InterruptedException {
        Log.d("DEBUGTAG", "getCommittees(string) called with " + bioguide_id);
        Request hey = new Request();
        return hey.execute("http://congress.api.sunlightfoundation.com/committees?member_ids="
                + bioguide_id + "&apikey=" + auth_key).get();
    }

    private void decodeJSONBills(String bills, RepBio rep_bio) {

        try {
            JSONObject bills0  = new JSONObject(bills);
            JSONArray results = bills0.getJSONArray("results");
            int count = bills0.getInt("count");
            String billsString = "";

            for (int i = 0; i < count && i < 6; i++) {
                String temp = "[ " + results.getJSONObject(i).getString("introduced_on") + "]"
                        + results.getJSONObject(i).getString("official_title");

                rep_bio.bills.add(temp);
                billsString += "> ";
                billsString += temp;
                billsString += "\n";
            }

            bill.setText(billsString);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void decodeJSONCommittees(String committees, RepBio rep_bio) {

        try {
            JSONObject committees0  = new JSONObject(committees);

//            Log.d("DEBUGTAG", twit.toString());
            JSONArray results = committees0.getJSONArray("results");
            int count = committees0.getInt("count");
            String billsString = "";

            for (int i = 0; i < count && i < 6; i++) {
                String temp = results.getJSONObject(i).getString("name");

                rep_bio.committees.add(temp);
                billsString += "> ";
                billsString += temp;
                billsString += "\n";
            }

            committe.setText(billsString);

        } catch (JSONException e) {
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

    private void set(RepBio rep_bio) {
        name.setText(rep_bio.rep_name);
        party.setText(rep_bio.party);
        emailad.setText(rep_bio.oc_email);
        website.setText(rep_bio.website);
        end.setText(rep_bio.term_ends_date);

    }

}
