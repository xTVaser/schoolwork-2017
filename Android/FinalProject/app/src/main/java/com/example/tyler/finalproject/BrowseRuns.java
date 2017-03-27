package com.example.tyler.finalproject;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BrowseRuns extends Fragment {

    private String gameID;
    private String gameName;
    private int categoryPos;

    protected ListView runList;

    private SearchPage searchPageFragment;
    private BrowseRuns browsePageFragment = this;
    private FavouriteGames favouritePageFragment;

    protected ProgressBar progressBar;

    public BrowseRuns() {
    }

    public void passFragments(Fragment[] fragments) {

        searchPageFragment = (SearchPage) fragments[0];
        favouritePageFragment = (FavouriteGames) fragments[1];
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        gameName = getArguments().getString("gameName");
        gameID = getArguments().getString("gameID");
        categoryPos = getArguments().getInt("categoryPos");
        getActivity().setTitle(gameName + " - " + getArguments().getString("title"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_run_list, container, false);

        progressBar = (ProgressBar) rootView.findViewById(R.id.progressBarRuns);

        GetRuns getRuns = new GetRuns(getActivity(), categoryPos, gameID, searchPageFragment, browsePageFragment, favouritePageFragment);
        Thread t = new Thread(getRuns);
        t.start();

        //Get the Runs
        List<Run> runs = new ArrayList<>();
        try {
            t.join();
            runs = getRuns.getValue();
            //Display the Runs
            runList = (ListView) rootView.findViewById(R.id.runList);
            RunAdapter adapter = new RunAdapter(getActivity(), R.layout.run, runs);
            runList.setAdapter(adapter);
        }
        catch (InterruptedException e) {
            Toast.makeText(getActivity(), "Error with threading", Toast.LENGTH_SHORT).show();
        }

        final List<Run> finalRuns = runs;
        runList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String link = finalRuns.get(position).runLink;
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(link));
                startActivity(intent);
            }
        });

        return rootView;
    }

    private JSONObject hasNextPage(JSONObject paginationData) throws Exception {

        if (paginationData == null)
            return null;

        JSONArray links = paginationData.getJSONArray("links");

        for (int i = 0; i < links.length(); i++) {

            JSONObject currentLink = links.getJSONObject(i);

            if (currentLink.getString("re").equals("next"))
                return new JSONParser(getActivity()).execute(currentLink.getString("uri")).get();
        }

        return null;
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
