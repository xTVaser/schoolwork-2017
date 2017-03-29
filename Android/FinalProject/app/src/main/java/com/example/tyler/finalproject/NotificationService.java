package com.example.tyler.finalproject;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class NotificationService extends IntentService {

    public NotificationService() {
        super("service");
    }

    public NotificationService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        //Get data from incoming intent
        String dataString = intent.getDataString();

        //Poll api and get the notifications
        SQLiteDatabase db = openOrCreateDatabase("srvFavourites", MODE_PRIVATE, null);
        Cursor cursor = db.rawQuery("SELECT * FROM favourites", null);
        cursor.moveToFirst();


        if (cursor.getCount() > 0) {
            do {

                final int notificationID = cursor.getInt(cursor.getColumnIndexOrThrow("_id"));
                final String previousRunID = cursor.getString(cursor.getColumnIndexOrThrow("last_notified_runid"));
                final String gameName = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                final String gameID = cursor.getString(cursor.getColumnIndexOrThrow("id"));
                String jsonURL = "http://www.speedrun.com/api/v1/runs?status=verified&orderby=verify-date&direction=desc&game=" + gameID;

                try {
                    JSONObject runData = getJSONSameThread(jsonURL);

                    JSONArray run = runData.getJSONArray("data");

                    //If no runs, get out
                    if (run.length() <= 0)
                        continue;

                    //Else get the first run
                    JSONObject firstRun = run.getJSONObject(0);


                    //If its the same run we've notified in the past
                    if (firstRun.getString("id").equals(previousRunID))
                        continue;

                    Intent notificationIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(firstRun.getString("weblink")));
                    PendingIntent contentIntent = PendingIntent.getActivity(getApplicationContext(), 0, notificationIntent, 0);

                    //Else notify user and update database
                    NotificationCompat.Builder mBuilder =
                            new NotificationCompat.Builder(getApplicationContext())
                                    .setSmallIcon(android.R.drawable.stat_notify_more)
                                    .setContentTitle("New Run Verified for " + gameName)
                                    .setContentText("View run now!").setContentIntent(contentIntent);

                    NotificationManager mNotifyMgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                    mNotifyMgr.notify(notificationID, mBuilder.build());

                    db.execSQL("UPDATE favourites SET last_notified_runid = '" + firstRun.getString("id") + "' WHERE id = '" + gameID + "'");
                }
                catch (Exception e) {
                    Log.e("JSONError", e.getMessage());
                }

            } while (cursor.moveToNext());
        }
        db.close();
    }

    public JSONObject getJSONSameThread(String url) {
        URL _url;
        JSONObject json = null;
        HttpURLConnection urlConnection;
        String output = "";

        try {
            _url = new URL(url);
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
            Log.e("JSONError", e.getMessage());
        }
        return json;
    }
}

