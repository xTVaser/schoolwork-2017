package com.example.tyler.finalproject;

import android.app.Activity;
import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class JSONParser extends AsyncTask<String, Object, JSONObject> {

    public JSONObject response;

    private Activity activity;

    public JSONParser() {

    }

    public JSONParser(Activity activity) {

        this.activity = activity;
    }

    @Override
    protected JSONObject doInBackground(String... params) {

        URL _url;
        JSONObject json = null;
        HttpURLConnection urlConnection;
        String output = "";

        try {
            _url = new URL(params[0]);
            urlConnection = (HttpURLConnection) _url.openConnection();
        }
        catch (MalformedURLException e) {
            return null;
        }
        catch (IOException e) {
            return null;
        }

        try {
            BufferedInputStream is = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder total = new StringBuilder(is.available());
            String line;
            while ((line = reader.readLine()) != null) {
                total.append(line).append('\n');
            }
            output = total.toString();
        }
        catch (IOException e) {
            return null;
        }
        finally{
            urlConnection.disconnect();
        }

        try {
            json = new JSONObject(output);
        }
        catch (JSONException e) {
        }
        response = json;
        return json;
    }
}
