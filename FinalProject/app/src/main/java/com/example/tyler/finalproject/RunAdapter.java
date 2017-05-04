package com.example.tyler.finalproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class RunAdapter extends ArrayAdapter<Run> {

    private Context ctx;
    private int listViewID;

    public RunAdapter(Context ctx, int listViewID) {

        super(ctx, listViewID);
    }

    public RunAdapter(Context ctx, int listViewID, List<Run> items) {

        super(ctx, listViewID, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = LayoutInflater.from(getContext()).inflate(R.layout.run, null);

        Run run = getItem(position);

        if (run != null) {

            TextView runRank = (TextView) view.findViewById(R.id.runRank);
            TextView runRunner = (TextView) view.findViewById(R.id.runRunner);
            TextView runTime = (TextView) view.findViewById(R.id.runTime);
            TextView runDate = (TextView) view.findViewById(R.id.runDate);

            runRank.setText(Integer.toString(run.rank));
            runRunner.setText(run.name);

            runTime.setText(run.time);

            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
            runDate.setText(dateFormat.format(run.submittedDate));
        }

        return view;
    }
}
