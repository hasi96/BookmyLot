package com.google.maps.android.utils.demo;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import java.io.IOException;
import java.util.List;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import android.graphics.Color;
import android.location.Location;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by hasitha on 5/12/15.
 */
public abstract class addlayout extends FragmentActivity implements OnMapClickListener, OnMapLongClickListener, OnMarkerClickListener {
    JSONObject json=new JSONObject();
    final LatLng[][] a = new LatLng[1000][1000];
    public GoogleMap mmap;
    int i=0;
    BitmapDescriptor bitmapDescriptor;
    int color;
    int allfeatures=0;
    boolean markerClicked;
    PolylineOptions rectOptions;
    Polyline polyline;
        public JSONObject createjson() {
            JSONObject jsonObject = new JSONObject();
            try {
                JSONArray json = new JSONArray();
                int row = 0;
                    while (a[row][0] != null) {
                        JSONObject featuredata = new JSONObject();
                        featuredata.put("type", "Feature");
                        JSONObject geometry = new JSONObject();
                        if (a[row][0].longitude == 0) {
                            geometry.put("type", "Point");
                        }
                        if (a[row][0].longitude == 1) {
                            geometry.put("type", "LineString");
                        }
                        JSONArray coords = new JSONArray();
                        int len = 1;
                        while (a[row][len] != null) {
                            JSONArray arr = new JSONArray();
                            double x = a[row][len].latitude;
                            double y = a[row][len].longitude;
                            arr.put(x);
                            arr.put(y);
                            coords.put(arr);
                            len = len + 1;
                        }
                        geometry.put("coordinates", coords);
                        featuredata.put("geometry", geometry);
                        json.put(featuredata);
                        row = row + 1;
                    }
                jsonObject.put("features", json);
                jsonObject.put("type", "FeatureCollection");
                Log.e("pleaseey", String.valueOf(jsonObject));
            } catch (JSONException je) {
            }
            return jsonObject;
        }
    protected int getLayoutId() {
        return R.layout.map;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());

        setUpMapIfNeeded();
    }
    public void onmarker(View view) throws IOException, JSONException {
        mmap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng point) {
                mmap.addMarker(new MarkerOptions().position(point).title(point.toString()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                a[i][0] = new LatLng(0, 0);
                a[i][1] = point;
                i = i + 1;
            }
        });
    }
    int k;
    public void onpolygon(View view) throws IOException{
        color = 17170450;
        k=1;
        bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.mipmap.redpoint);
        mmap.setOnMapLongClickListener(this);
      mmap.setOnMarkerClickListener(this);
        i=i+1;
        }

    public void onMapClick (LatLng point) {

                   mmap.animateCamera(CameraUpdateFactory.newLatLng(point));
                   markerClicked = false;
    }
    @Override
    public void onMapLongClick(LatLng point) {
        mmap.addMarker(new MarkerOptions().position(point).title(point.toString()).icon(bitmapDescriptor));
        markerClicked = false;
    }


    public boolean onMarkerClick(Marker marker) {
        if(i==0){i=i+1;}
        a[i-1][0]=new LatLng(0,1);
        if (markerClicked) {
            rectOptions.add(marker.getPosition());
            rectOptions.color(Color.RED);
            polyline = mmap.addPolyline(rectOptions);
            a[i - 1][k] = marker.getPosition();
            k = k + 1;
        }
        else {
            a[i - 1][k] = marker.getPosition();
            k = k + 1;
            rectOptions = new PolylineOptions().add(marker.getPosition());
            markerClicked = true;
        }
        return true;
    }


    public void onpath(View view) throws IOException
    {
        color = 17170452;
        bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.mipmap.redpoint);
        mmap.setOnMapLongClickListener(this);
        mmap.setOnMarkerClickListener(this);
    }

    public void onsave(View view)
    {
        allfeatures=1;
        JSONObject GEOJSON =  createjson();
        allfeatures=0;
    }


    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }
    private void setUpMapIfNeeded() {
        if (mmap != null) {
            return;
        }
        mmap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
        mmap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        if (mmap != null) {
            startDemo();
        }
    }
    protected abstract void startDemo();

    protected GoogleMap getMap() {

        setUpMapIfNeeded();
        return mmap;
    }


}
