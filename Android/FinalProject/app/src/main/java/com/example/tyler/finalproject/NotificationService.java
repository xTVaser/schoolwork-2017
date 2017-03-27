package com.example.tyler.finalproject;

import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;


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

                int notificationID = cursor.getInt(cursor.getColumnIndexOrThrow("_id"));
                NotificationCompat.Builder mBuilder =
                        new NotificationCompat.Builder(this)
                                .setSmallIcon(android.R.drawable.stat_notify_more)
                                .setContentTitle("My notification")
                                .setContentText(cursor.getString(cursor.getColumnIndexOrThrow("id")));

                NotificationManager mNotifyMgr = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);

                // TODO: finish this, these will also have to be translated as well
                // add another column to the DB that tracks the last runID notified
                // this will prevent duplicate runs from being notified

                // TODO: set intent for the notification
                mNotifyMgr.notify(notificationID, mBuilder.build());

            } while (cursor.moveToNext());
        }

        db.close();

    }
}
