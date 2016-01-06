

package com.google.maps.android.utils.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.maps.android.utils.demo.model.MyMarker;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends Activity implements View.OnClickListener {
    private ViewGroup mListView;
    private ArrayList<MyMarker> mMyMarkersArray = new ArrayList<MyMarker>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);


        mListView = (ViewGroup) findViewById(R.id.list);


        addlayer("Search for parking lots", GeoJsonDemoActivity.class);
        addlayer("Add Layouts",Layout.class);

    }

    private void addlayer(String demoName, Class<? extends Activity> activityClass){
        Button b = new Button(this);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        b.setLayoutParams(layoutParams);
        b.setText(demoName);
        b.setTag(activityClass);
        b.setOnClickListener(this);
        mListView.addView(b);
    }


    @Override
    public void onClick(View view) {
        Class activityClass = (Class) view.getTag();
        startActivity(new Intent(this, activityClass));
        new DownloadWebpageTask(new AsyncResult() {
            @Override
            public void onResult(JSONObject object) {
                processJson(object);
            }
        }).execute("https://spreadsheets.google.com/tq?key=1dxIC84LRMuoGwP-qxJuaERIIhc99ow87-zLshVLRO98");
    }
    private void processJson(JSONObject object) {

        try {
            JSONArray rows = object.getJSONArray("rows");

            for (int r = 0; r < rows.length(); ++r) {
                JSONObject row = rows.getJSONObject(r);
                JSONArray columns = row.getJSONArray("c");
                Double latitude = columns.getJSONObject(0).getDouble("v");
                Double longitude = columns.getJSONObject(1).getDouble("v");
                String image = columns.getJSONObject(2).getString("v");
                mMyMarkersArray.add(new MyMarker(latitude,longitude,image));

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
