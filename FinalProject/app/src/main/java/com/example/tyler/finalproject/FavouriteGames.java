package com.example.tyler.finalproject;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class FavouriteGames extends Fragment {

    ArrayList<String> gameIDs = new ArrayList<>();
    ArrayList<String> gameNames = new ArrayList<>();

    protected ListView favouriteList;
    protected ProgressBar progressBar;

    public FavouriteGames() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_favourite_games, container, false);

        favouriteList = (ListView) rootView.findViewById(R.id.favouriteList);
        progressBar = (ProgressBar) rootView.findViewById(R.id.progressBarFavourites);

        SQLiteDatabase db = getActivity().openOrCreateDatabase("srvFavourites", MODE_PRIVATE, null);
        Cursor cursor = db.rawQuery("SELECT * FROM favourites", null);

        FavouriteAdapter adapter = new FavouriteAdapter(getActivity(), cursor);
        favouriteList.setAdapter(adapter);

        cursor.moveToFirst();
        // Put all of the game IDs into a ArrayList so we can delete them later
        if (cursor.getCount() > 0) {
            do {
                gameIDs.add(cursor.getString(cursor.getColumnIndexOrThrow("id")));
                gameNames.add(cursor.getString(cursor.getColumnIndexOrThrow("name")));
            } while (cursor.moveToNext());
        }
        db.close();

        favouriteList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                openBrowsePage(position);
            }
        });

        favouriteList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                removeFavouriteDialog(position);
                // Prevents later events from firing
                return true;
            }
        });

        return rootView;
    }

    private void openBrowsePage(int position) {

        SQLiteDatabase db = getActivity().openOrCreateDatabase("srvFavourites", MODE_PRIVATE, null);
        Cursor cursor = db.rawQuery("SELECT * FROM favourites", null);
        cursor.moveToPosition(position);

        ((MainActivity)getActivity()).updateDrawerAndDisplay(
                cursor.getString(cursor.getColumnIndexOrThrow("id")),
                cursor.getString(cursor.getColumnIndexOrThrow("name")));
    }

    private void removeFavourite(int position) {

        SQLiteDatabase db = getActivity().openOrCreateDatabase("srvFavourites", MODE_PRIVATE, null);
        db.execSQL("DELETE FROM favourites WHERE id = '" + gameIDs.get(position) + "'");

        gameIDs.remove(position);
        gameNames.remove(position);

        Cursor cursor = db.rawQuery("SELECT * FROM favourites", null);
        cursor.moveToFirst();
        FavouriteAdapter adapter = new FavouriteAdapter(getActivity(), cursor);
        db.close();
        favouriteList.setAdapter(adapter);
    }

    private void removeFavouriteDialog(final int position) {

        // Display dialog to confirm
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle(Html.fromHtml("<font color='#c6ff00'>Deletion Confirmation</font>"))
                .setMessage(Html.fromHtml("<font color='#fffde7'>Are you sure you want to delete " + gameNames.get(position) + " from favourites?</font>"))
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        removeFavourite(position);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void onActivityCreated(Bundle savedState) {
        super.onActivityCreated(savedState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
