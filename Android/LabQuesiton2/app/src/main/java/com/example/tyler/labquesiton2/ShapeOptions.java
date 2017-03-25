package com.example.tyler.labquesiton2;

/**
 * Created by tyler on 20/03/17.
 */

import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class ShapeOptions extends ListFragment {

    public static String[] mTitleArray;

    public ShapeOptions() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedState) {
        super.onActivityCreated(savedState);

        // Set the list adapter for the ListView
        mTitleArray = getResources().getStringArray(R.array.Shapes);
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                mTitleArray);
        setListAdapter(myAdapter);
    }

    // Called when the user selects an item from the List
    @Override
    public void onListItemClick(ListView l, View v, int pos, long id) {

        ((MainActivity)getActivity()).updateShape(pos);
    }
}
