package com.example.tyler.finalproject;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;


public class FavouriteGames extends Fragment {

    ArrayList<String> gameIDs = new ArrayList<>();
    ArrayList<String> gameNames = new ArrayList<>();

    ListView favouriteList;

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

        ((MainActivity)getActivity()).updateDrawerAndDisplay(gameIDs.get(position), gameNames.get(position));
    }

    private void removeFavourite(int position) {

        SQLiteDatabase db = getActivity().openOrCreateDatabase("srvFavourites", MODE_PRIVATE, null);
        db.execSQL("DELETE FROM favourites WHERE id = '" + gameIDs.get(position) + "'");

        gameIDs.remove(position);
        gameNames.remove(position);

        Cursor cursor = db.rawQuery("SELECT * FROM favourites", null);
        cursor.moveToFirst();
        FavouriteAdapter adapter = new FavouriteAdapter(getActivity(), cursor);
        favouriteList.setAdapter(adapter);
    }

    private void removeFavouriteDialog(final int position) {

        // Display dialog to confirm
        new AlertDialog.Builder(getActivity())
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Deletion Confirmation")
                .setMessage("Are you sure you want to delete " + gameNames.get(position) + " from favourites?")
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
                })
                .show();
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
