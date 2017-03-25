package com.example.tyler.testlistfragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class DescriptionsFragment extends Fragment {

    private TextView mdescriptionView = null;
    public static String[] mDescriptionArray;

    public DescriptionsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_descriptions, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mDescriptionArray = getResources().getStringArray(R.array.Descriptions);
        mdescriptionView = (TextView) getActivity().findViewById(R.id.descriptionView);
    }

    // Show the course description at position newIndex
    public void showDescriptionAtIndex(int newIndex) {
        if (newIndex < 0 || newIndex >= mDescriptionArray.length)
            return;

        mdescriptionView.setText(mDescriptionArray[newIndex]);
    }

}
