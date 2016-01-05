package com.google.maps.android.utils.demo;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.maps.android.geojson.GeoJsonFeature;
import com.google.maps.android.geojson.GeoJsonLayer;
import com.google.maps.android.geojson.GeoJsonPointStyle;
import org.json.JSONException;
import org.json.JSONObject;
import android.os.AsyncTask;
import android.util.Log;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

public class GeoJsonActivity extends BaseDemoActivity {

    private final static String mLogTag = "GeoJsonDemo";

    // GeoJSON file to download
    private final String mGeoJsonUrl
            = "https://api.myjson.com/bins/1wbkv";

    private GeoJsonLayer mLayer;



    protected int getLayoutId() {
        return R.layout.geojson_demo;
    }

    @Override
    protected void startDemo() {
        DownloadGeoJsonFile downloadGeoJsonFile = new DownloadGeoJsonFile();
        // Download the GeoJSON file
        downloadGeoJsonFile.execute(mGeoJsonUrl);
    }



    private class DownloadGeoJsonFile extends AsyncTask<String, Void, JSONObject> {

        @Override
        protected JSONObject doInBackground(String... params) {
            try {
                // Open a stream from the URL
                InputStream stream = new URL(params[0]).openStream();

                String line;
                StringBuilder result = new StringBuilder();
                BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

                while ((line = reader.readLine()) != null) {
                    // Read and save each line of the stream
                    result.append(line);
                }

                // Close the stream
                reader.close();
                stream.close();

                // Convert result to JSONObject
                return new JSONObject(result.toString());
            } catch (IOException e) {
                Log.e(mLogTag, "GeoJSON file could not be read");
            } catch (JSONException e) {
                Log.e(mLogTag, "GeoJSON file could not be converted to a JSONObject");
            }
            return null;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            if (jsonObject != null) {
                // Create a new GeoJsonLayer, pass in downloaded GeoJSON file as JSONObject
                mLayer = new GeoJsonLayer(getMap(), jsonObject);
                // Add the layer onto the map
                mLayer.addLayerToMap();
            }
        }


    }
}

