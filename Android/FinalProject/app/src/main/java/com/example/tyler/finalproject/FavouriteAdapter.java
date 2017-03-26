package com.example.tyler.finalproject;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;


public class FavouriteAdapter extends CursorAdapter {

    public FavouriteAdapter(Context ctx, Cursor cursor) {

        super(ctx, cursor, 0); // 0 = flags
    }

    @Override
    public View newView(Context ctx, Cursor cursor, ViewGroup parent) {

        return LayoutInflater.from(ctx).inflate(R.layout.favourite, parent, false);
    }

    @Override
    public void bindView(View view, Context ctx, Cursor cursor) {

        ImageView pic = (ImageView) view.findViewById(R.id.favGamePic);
        TextView name = (TextView) view.findViewById(R.id.favGameName);

        //Set image
        new ExternalImage(pic).execute(cursor.getString(cursor.getColumnIndexOrThrow("picurl")));
        //Set the name
        name.setText(cursor.getString(cursor.getColumnIndexOrThrow("name")));
    }

    private class ExternalImage extends AsyncTask<String, Void, Bitmap> {

        ImageView imageContainer;

        public ExternalImage(ImageView imageContainer) {

            this.imageContainer = imageContainer;
        }

        protected Bitmap doInBackground(String... params) {

            String url = params[0];
            Bitmap image = null;

            try {

                InputStream in = new URL(url).openStream();
                image = BitmapFactory.decodeStream(in);
            }
            catch (Exception e) {
                System.out.println("Error when retrieving image");
            }
            return image;
        }

        protected void onPostExecute(Bitmap image) {

            imageContainer.setImageBitmap(image);
        }
    }
}

