package com.example.tyler.imagesfragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class ImageFragment extends Fragment {

    ImageView image;
    TextView title;
    TextView desc;

    public ImageFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    //cuz View isnt there until its created
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        image = (ImageView) getView().findViewById(R.id.fragImage);
        title = (TextView) getView().findViewById(R.id.fragTitle);
        desc = (TextView) getView().findViewById(R.id.fragDescription);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_image, container, false);
    }

    public void updateViews(int resource, String title, String desc) {

        image.setImageResource(resource);
        this.title.setText(title);
        this.desc.setText(desc);
    }
}
